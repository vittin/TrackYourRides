package com.example.service;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Mateusz on 2016-08-02.
 */

public class AuthServiceImplTest {

    private AuthService authService;

    @Before
    public void setUp(){
        authService = new AuthServiceImpl();
    }

    @Test
    public void shouldEncodePassword(){

        String plainText = "123abc";
        String password = authService.encrypt(plainText);
        System.out.println(password);

        assertThat(plainText, is(not(password)));
        assertThat(password.length(), is(not(plainText.length())));

    }

    @Test
    public void shouldMatch(){
        String plainText = "123abc";
        String hash1 = authService.encrypt(plainText);
        String hash2 = authService.encrypt(plainText);

        assertTrue(authService.validatePassword(plainText, hash1));
        assertTrue(authService.validatePassword(plainText, hash2));
    }

    @Test
    public void encoderIsConstantAndOneDirection(){
        String plainText = "123abc";
        String password1 = authService.encrypt(plainText);
        String password2 = authService.encrypt(plainText);
        String password3 = authService.encrypt(password1);

        assertTrue(authService.validatePassword(plainText, password1));
        assertTrue(authService.validatePassword(plainText, password2));

        assertFalse(authService.validatePassword(plainText, password3));
        assertFalse(authService.validatePassword(password1, plainText));
    }
}