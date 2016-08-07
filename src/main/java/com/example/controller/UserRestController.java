package com.example.controller;

import com.example.model.Token;
import com.example.model.User;
import com.example.service.UserService;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.sun.org.apache.xml.internal.utils.LocaleUtility.EMPTY_STRING;

/**
 * Created by Mateusz on 2016-08-01.
 */

@RestController
@RequestMapping(value = "api/users")
@Scope("session")
public class UserRestController {

    private static final String DOMAIN = "127.0.0.1";
    private final UserService userService;

    @Autowired
    UserRestController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public HttpEntity<String> register
            (@RequestBody User presentUser, @CookieValue(value = "sessionToken", defaultValue = "") String cookie){

        if(userService.hasActiveSession(cookie)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ApiMessage.alreadyLoggedIn());
        }

        String email = presentUser.getEmail();

        if (!userService.userIsUnique(presentUser)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiMessage.registerFailed());
        } else if (!userService.emailIsValid(email)){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiMessage.incorrectEmail());
        } else {
            userService.createUser(presentUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiMessage.userCreated(presentUser));
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HttpEntity<String> login
            (HttpSession session, @RequestBody User presentUser,
             HttpServletResponse response, @CookieValue(value = "sessionToken", defaultValue = "") String requestCookie){

        if(userService.hasActiveSession(requestCookie)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ApiMessage.alreadyLoggedIn());
        }

        presentUser = checkIfIsUsernameOrPassword(presentUser);

        User user = userService.findUser(presentUser);

        if (user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiMessage.loginFailed());
        }

        if (!userService.canLogin(presentUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiMessage.loginFailed());
        } else {

            Token token = userService.createTokenFor(user.getId(), session.getId());
            String tokenId = token.getTokenId();

            Cookie cookie = prepareCookie(tokenId);
            response.addCookie(cookie);

            return ResponseEntity.ok().body(ApiMessage.loginSuccessful());

        }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public HttpEntity logout(@CookieValue(value = "sessionToken", defaultValue = "") String token,
                             HttpServletResponse response){
        Cookie cookie = prepareCookie(null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        userService.logout(token);

        return ResponseEntity.ok().body(ApiMessage.loggedOut());
    }

    @RequestMapping(value = "/logged", method = RequestMethod.GET)
    public HttpEntity isLogged(@CookieValue(value = "sessionToken", defaultValue = "") String token,
                           HttpServletResponse response){
        boolean activeSession = userService.hasActiveSession(token);

        if(!activeSession && !EMPTY_STRING.equals(token)){
            logout(token, response);
        }

        return ResponseEntity.ok().body(ApiMessage.hasActiveSession(activeSession));
    }

    private User checkIfIsUsernameOrPassword(User presentUser) {
        presentUser.setEmail("");
        if(userService.emailIsValid(presentUser.getUsername())){
            presentUser = new User("", presentUser.getPassword(), presentUser.getEmail());
        }
        return presentUser;
    }

    private Cookie prepareCookie(@Nullable String tokenId){
        Cookie responseCookie = new Cookie("sessionToken", tokenId);
        responseCookie.setDomain(DOMAIN);
        responseCookie.setPath("/");
        responseCookie.setMaxAge(30*60);
        return responseCookie;
    }


}
