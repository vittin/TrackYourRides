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
    public Token getToken(String cookie){
        return tokenRepo.findByTokenId(cookie);
    }

    @Override
    public void saveToken(Token token, long time){
        token.setExpiredTime(time);
        tokenRepo.save(token);
    }

    @Override
    public boolean isTokenValid(String tokenId){

        boolean authorized = false;
        Token token = getToken(tokenId);
        if (token != null){
            if (token.getExpiredTime() > System.currentTimeMillis()){
                authorized = true;
            } else {
                deleteToken(tokenId);
            }
        }
        return authorized;
    }

    @Override
    @Nullable
    public Long getUserByToken(String cookie) {
        Token token = tokenRepo.findByTokenId(cookie);
        Long userId = null;

        if (token != null){
            userId = token.getUserId();
        }

        return userId;
    }

    @Override
    public void deleteToken(String tokenId){
        Token token = getToken(tokenId);
        if (tokenRepo.findOne(token.getEntityId()) != null){
            tokenRepo.delete(token);
        }

    }

}
