package com.example.service;

import com.example.model.User;
import com.example.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Component
public class UserServiceImpl implements UserService {

    private UserRepository repo;
    private AuthService authService;

    @Autowired
    UserServiceImpl(UserRepository repo, AuthService authService){
        this.repo = repo;
        this.authService = authService;
    }

    @Override
    public boolean userIsUnique(User user){
        return false;
    }

    @Override
    public User findUser(String username){
        return repo.findByUsername(username);
    }

    @Override
    public String createTokenFor(Long id) {
        return "";
    }

    @Override
    public User createUser(User user) {
        return repo.save(user);
    }

    @Override
    public boolean identityIsConfirmed(User user){
        String username = user.getUsername();
        String password = authService.encrypt(user.getPassword());

        return findUser(username).getPassword().equals(password);
    }

    @Override
    public boolean emailIsValid(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*\n" +
                "      @[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$;";
        return email.matches(emailPattern);
    }
}
