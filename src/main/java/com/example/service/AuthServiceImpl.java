package com.example.service;

import com.example.model.Token;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Mateusz on 2016-07-31.
 */

@Component
public class AuthServiceImpl implements AuthService{

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encrypt(String plainText){
        return encoder.encode(plainText);
    }

    @Override
    public boolean validatePassword(String plainText, String hash){
        return encoder.matches(plainText, hash);
    }

    @Override
    public Token createToken(long userId, String sessionId){

        String token = UUID.randomUUID().toString();

        return new Token(userId, sessionId, token);
    }

    @Override
    public long verifyUser(String token) {
        return 0;
    }


}
