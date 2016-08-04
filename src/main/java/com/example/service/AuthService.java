package com.example.service;

import com.example.model.Token;
import org.springframework.stereotype.Service;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Service
public interface AuthService {

    String encrypt(String plainText);

    boolean validatePassword(String password, String hash);

    Token createToken(long userId, String sessionId);

    void saveToken(Token token, long time);

    Long getUserByToken(String token);

    Token getTokenFor(Long id);
}
