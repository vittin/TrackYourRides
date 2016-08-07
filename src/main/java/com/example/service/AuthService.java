package com.example.service;

import com.example.model.Token;

/**
 * Created by Mateusz on 2016-07-28.
 */

public interface AuthService {

    String encrypt(String plainText);

    boolean validatePassword(String password, String hash);

    Token createToken(long userId, String sessionId);

    void saveToken(Token token, long time);

    Long getUserByToken(String token);

    Token getToken(String token);

    boolean isTokenValid(String tokenId);

    void deleteToken(String token);
}
