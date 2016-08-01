package com.example.controller;

import com.example.model.User;
import com.example.service.UserServiceImpl;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mateusz on 2016-08-01.
 */

@RestController
@RequestMapping("users/")
public class UserRestController {

    private final UserServiceImpl userService;

    @Autowired
    UserRestController(UserServiceImpl userService){
        this.userService = userService;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public HttpEntity<String> login(User presentUser, @Nullable String previousPage){

        User user = userService.findUser(presentUser.getUsername());

        if (user == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiMessage.loginFailed());
        }


        if (userService.identityIsConfirmed(presentUser)){
            String token = userService.createTokenFor(user.getId());
            HttpHeaders headers = new HttpHeaders();
            headers.add("setCookie", "sessionToken="+token);
            return ResponseEntity.ok().headers(headers).body(ApiMessage.loginSuccessful(previousPage));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiMessage.loginFailed());
        }

    }

    @RequestMapping(value = "users", method = RequestMethod.POST)
    public HttpEntity<String> register(User presentUser){

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
}
