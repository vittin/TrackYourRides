package com.example.service;

import org.springframework.stereotype.Component;

/**
 * Created by Mateusz on 2016-07-31.
 */

@Component
public class AuthServiceImpl implements AuthService{

    @Override
    public String encrypt(String plainText){

        return plainText;
    }

    @Override
    public String createToken(long userId){
        return "3sd";
    }
}
