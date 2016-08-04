package com.example.service;

import com.example.model.Token;
import com.example.model.User;
import org.springframework.stereotype.Service;

/**
 * Created by Mateusz on 2016-08-01.
 */

@Service
public interface UserService {

    boolean userIsUnique(User user);

    User findUser(User user);

    User findUser(Long id);

    User findUser(String token);

    Token createTokenFor(Long userId, String sessionId);

    User createUser(User user);

    boolean emailIsValid(String email);

    boolean canUserLogIn(User user);

    boolean hasActiveSession(String cookie);

    Token getTokenFor(Long userId);


    //User getCurrentUser();

    //void setCurrentUser();
}
