package com.example.service;

import com.example.model.Token;
import com.example.model.TokenRepository;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;

/**
 * Created by Mateusz on 2016-08-02.
 */

public class AuthServiceImplTest {

    private AuthService authService;
    private TokenRepository tokenRepository;

    @Before
    public void setUp(){

        tokenRepository = mock(TokenRepository.class);

        authService = new AuthServiceImpl(tokenRepository);
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

    @Test
    public void tokensShouldBeUnique(){

        Token token1 = authService.createToken(1L, "abc");
        Token token2 = authService.createToken(1L, "def");
        Token token3 = authService.createToken(2L, "abc");

        assertThat(token1, is(not(token2)));
        assertThat(token1, is(not(token3)));

        assertThat(token1.getUserId(), is(token2.getUserId()));
        assertThat(token1.getSession(), is(token3.getSession()));
    }

}