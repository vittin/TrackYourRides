package com.example.service;

import com.example.model.Token;
import com.example.model.TokenRepository;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Mateusz on 2016-07-31.
 */

@Service
public class AuthServiceImpl implements AuthService{

    private TokenRepository tokenRepo;

    @Autowired
    public AuthServiceImpl(TokenRepository tokenRepo) {
        this.tokenRepo = tokenRepo;
    }





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
    @Nullable
    public Token getTokenFor(Long id){
        return tokenRepo.findByUserId(id);
    }

    @Override
    public void saveToken(Token token, long time){
        token.setExpiredTime(time);
        tokenRepo.save(token);
    }

    @Override
    @Nullable
    public Long getUserByToken(String cookie) {
        Token token = tokenRepo.findByToken(cookie);
        Long userId = null;

        if (token != null){
            userId = token.getUserId();
        }

        return userId;
    }

}
