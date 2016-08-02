package com.example.service;

import com.example.model.Token;
import com.example.model.TokenRepository;
import com.example.model.User;
import com.example.model.UserRepository;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final AuthService authService;
    private final TokenRepository tokenRepo;

    @Autowired
    UserServiceImpl(UserRepository repo, TokenRepository tokenRepo, AuthService authService){
        this.repo = repo;
        this.tokenRepo = tokenRepo;
        this.authService = authService;
    }

    @Override
    public boolean userIsUnique(User user)
    {

        User userFromRepo = findUser(user);

        return (userFromRepo == null);
    }

    @Override
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
    public String createTokenFor(Long id, String sessionId) {
        Token token = authService.createToken(id, sessionId);
        tokenRepo.save(token);
        return token.toString();
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
}
