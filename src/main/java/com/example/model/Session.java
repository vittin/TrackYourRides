package com.example.model;

/**
 * Created by Mateusz on 2016-07-28.
 */

public class Session {

    private String login;
    private String token;

    public Session(String login, String token){
        this.login = login;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return token;
    }
}
