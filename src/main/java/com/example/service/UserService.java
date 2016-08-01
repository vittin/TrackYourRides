package com.example.service;

import com.example.model.User;

/**
 * Created by Mateusz on 2016-08-01.
 */

public interface UserService {

    boolean userIsUnique(User user);

    User findUser(String username);

    String createTokenFor(Long userId);

    User createUser(User user);

    boolean emailIsValid(String email);

    boolean identityIsConfirmed(User user);
}
