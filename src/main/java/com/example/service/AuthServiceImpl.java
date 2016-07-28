package com.example.service;

import org.springframework.stereotype.Component;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Component
public class AuthServiceImpl implements AuthService {


    @Override
    public boolean checkCredentials(String login, String password) {
        //getPasswordWhereLogin == login;
        //check if password == password;
        return false;
    }

    @Override
    public boolean hasActiveSession(String login, String token){

        //check if token generated at (time) is equals with given token
        //check if token is still valid

        return false;
    }

    @Override
    public String createNewToken(String login) {
        //get User private key
        //get time
        //create token for 30min


        return null;
    }
}
