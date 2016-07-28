package com.example.service;

import com.example.model.User;
import org.apache.tomcat.jni.Time;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.sql.Date;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Component
public class UserService {

    public User newUser(String login, String password){
        User user = new User();
        String privateKey = generatePrivateKey();
        user.setPrivateKey(privateKey);
        newSession(user);
        return user;
    }

    private String generatePrivateKey() {

        return UUID.randomUUID().toString();
    }

    private void newSession(User user) {
        Date date = new Date(Time.now());
        String token = generateToken(date);
        user.setTokenDate(date);
        user.setToken(token);
    }

    private String generateToken(Date date) {

        return UUID.randomUUID().toString();
    }
}
