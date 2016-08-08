package com.example.service;

import com.example.model.Token;
import com.example.repository.TokenRepository;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Mateusz on 2016-08-02.
 */

public class UserServiceImplTest {

    private UserService userService;
    @Mock private UserRepository userRepo;
    @Mock private TokenRepository tokenRepo;
    @Mock private AuthService authService;
    @Mock private User fakeUser;

    @Before
    public void setUp(){

        this.userRepo = mock(UserRepository.class);
        this.authService = mock(AuthService.class);

        this.userService = new UserServiceImpl(userRepo, authService);

        this.fakeUser = new User("john", "hashedPassword", "email@email.com");
    }

    @Test
    public void shouldNotAllowRegistration(){

        User testedUser1 = new User("john", "anotherHashedPassword", "abc@email.com");
        User testedUser2 = new User("Kevin", "admin1", "email@email.com");

        when(userRepo.findByUsername("john")).thenReturn(fakeUser);
        when(userRepo.findByUsername("kevin")).thenReturn(null);
        when(userRepo.findByEmail("email@email.com")).thenReturn(fakeUser);
        when(userRepo.findByEmail("abc@email.com")).thenReturn(null);

        assertFalse(userService.userIsUnique(testedUser1));
        assertFalse(userService.userIsUnique(testedUser2));

    }

    @Test
    public void shouldAllowRegistration(){

        User testedUser1 = new User("john", "anotherHashedPassword", "abc@email.com");
        User testedUser2 = new User("kevin", "admin1", "email@email.com");

        when(userRepo.findByUsername("john")).thenReturn(null);
        when(userRepo.findByEmail("email@email.com")).thenReturn(null);

        assertTrue(userService.userIsUnique(testedUser1));
        assertTrue(userService.userIsUnique(testedUser1));

    }

    @Test
    public void shouldFoundUserByUsername(){

        User john = new User("John", "hashedPassword", "abc@email.com");
        User marry = new User("marry", "anotherPass", "another@ema.il");

        when(userRepo.findByUsername("john")).thenReturn(fakeUser);
        when(userRepo.findByUsername("marry")).thenReturn(null);


        assertThat(userService.findUser(john), is(fakeUser));
        assertThat(userService.findUser(marry), is(not(fakeUser)));
        assertThat(userService.findUser(marry), is(nullValue()));
    }

    @Test
    public void shouldFoundUserByEmail(){

        User john = new User("kevin", "hashedPassword", "email@email.com");
        User marry = new User("marry", "anotherPass", "abc@email.com");

        when(userRepo.findByEmail("email@email.com")).thenReturn(fakeUser);
        when(userRepo.findByUsername("abc@email.com")).thenReturn(null);

        assertThat(userService.findUser(john), is(fakeUser));
        assertThat(userService.findUser(marry), is(not(fakeUser)));
        assertThat(userService.findUser(marry), is(nullValue()));
    }

    @Test
    public void shouldCreateToken(){
        Token token = new Token(1L, "3124h", "token-223");
        when(authService.createToken(1L, "3124h")).thenReturn(token);

        Token createdToken = userService.createTokenFor(1L, "3124h");

        assertThat(createdToken, is(token));
        verify(tokenRepo).save(token);

    }

    @Test
    public void shouldCreateUser(){
        userService.createUser(fakeUser);
        verify(userRepo).save(fakeUser);
    }

    @Test
    public void shouldVerifyUser(){

        User john = new User("John", "password", "email@email.com");
        User marry = new User("Marry", "anotherPassword", "abc@email.com");

        User marryFromRepo = new User("Marry", "differentHash", "abc@email.com");

        when(userRepo.findByUsername("john")).thenReturn(fakeUser);
        when(userRepo.findByUsername("marry")).thenReturn(marryFromRepo);

        when(authService.validatePassword("password", "hashedPassword")).thenReturn(true);
        when(authService.validatePassword("anotherPassword", "anotherHash")).thenReturn(false);

        assertTrue(userService.canLogin(john));
        assertFalse(userService.canLogin(marry));
    }

    @Test
    public void shouldVerifyEmail(){

        assertTrue(userService.emailIsValid("email@email.com"));
        assertTrue(userService.emailIsValid("abc@em.xz"));
        assertFalse(userService.emailIsValid("john.xyz"));
        assertFalse(userService.emailIsValid("email@email"));
    }

}