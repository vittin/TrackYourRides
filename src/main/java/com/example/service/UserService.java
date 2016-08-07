package com.example.service;

import com.example.model.Token;
import com.example.model.User;

/**
 * Created by Mateusz on 2016-08-01.
 */

public interface UserService {

    boolean userIsUnique(User user);

    User findUser(User user);

    User findUser(String token);

    Token createTokenFor(Long userId, String sessionId);

    User createUser(User user);

    boolean emailIsValid(String email);

    boolean canLogin(User user);

    boolean hasActiveSession(String cookie);

    Token getToken(String cookie);

    void logout(String token);
}
