package com.example.service;

import com.example.model.User;

/**
 * Created by Mateusz on 2016-08-01.
 */

public interface UserService {

    boolean userIsUnique(User user);

    User findUser(User user);

    String createTokenFor(Long userId, String sessionId);

    User createUser(User user);

    boolean emailIsValid(String email);

    boolean canUserLogIn(User user);
}
