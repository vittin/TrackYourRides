package com.example.service;

/**
 * Created by Mateusz on 2016-07-28.
 */

public interface AuthService {

    boolean checkCredentials(String login, String password);

    boolean hasActiveSession(String login, String token);

    String createNewToken(String login);
}
