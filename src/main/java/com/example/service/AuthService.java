package com.example.service;

/**
 * Created by Mateusz on 2016-07-28.
 */

public interface AuthService {

    String encrypt(String plainText);

    String createToken(long userId);

}
