package com.example.service;

import com.example.model.Token;

/**
 * Created by Mateusz on 2016-07-28.
 */

public interface AuthService {

    String encrypt(String plainText);

    boolean validatePassword(String password, String hash);

    Token createToken(long userId, String sessionId);

    long verifyUser(String token);
}
