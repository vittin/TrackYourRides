package com.example.service;

import com.example.model.Token;
import com.example.model.User;
import com.example.model.UserRepository;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Service
public class UserServiceImpl implements UserService {

    private static final long DEFAULT_COOKIE_EXPIRATION_TIME = 30;
    private final UserRepository repo;
    private final AuthService authService;

    @Autowired
    public UserServiceImpl(UserRepository repo, AuthService authService){
        this.repo = repo;
        this.authService = authService;
    }

    @Override
    public boolean userIsUnique(User user)
    {

        User userFromRepo = findUser(user);

        return (userFromRepo == null);
    }

    @Override
    @Nullable
    public User findUser(User givenUser){


        System.out.println(givenUser);
        String username = givenUser.getUsername().toLowerCase();
        String email = givenUser.getEmail().toLowerCase();

        User user = repo.findByUsername(username);
        if (user == null){
            user = repo.findByEmail(email);
        }

        return user;
    }

    @Override
    @Nullable
    public User findUser(Long id){
        return repo.getOne(id);
    }

    @Override
    @Nullable
    public User findUser(String token){

        Long id = authService.getUserByToken(token);
        return repo.getOne(id);
    }

    @Override
    public Token createTokenFor(Long id, String sessionId) {
        Token token = authService.createToken(id, sessionId);
        authService.saveToken(token, DEFAULT_COOKIE_EXPIRATION_TIME);
        return token;
    }

    @Override
    @Nullable
    public Token getTokenFor(Long id){
        return authService.getTokenFor(id);
    }

    @Override
    public User createUser(User user) {
        String plainTextPassword = user.getPassword();
        user.setPassword(authService.encrypt(plainTextPassword));
        return repo.save(user);
    }

    @Override
    public boolean canUserLogIn(User user){

        String password = user.getPassword();

        User userFromRepo = findUser(user);
        if (userFromRepo == null){
            return false;
        }


        String passwordHash = userFromRepo.getPassword();


        System.out.println(password);
        System.out.println(passwordHash);

        return authService.validatePassword(password, passwordHash);
    }

    @Override
    public boolean emailIsValid(@Nullable String email) {

        if(email == null){
            return false;
        }

        String emailPattern = "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)";
        return email.matches(emailPattern);
    }

    @Override
    public boolean hasActiveSession(String token){
        return authService.getUserByToken(token) != null;
    }
}
