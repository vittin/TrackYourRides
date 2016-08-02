package com.example.controller;

import com.example.model.User;
import com.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Mateusz on 2016-08-01.
 */

@RestController
@RequestMapping(value = "api/users")
@Scope("session")
public class UserRestController {

    private final UserServiceImpl userService;

    @Autowired
    UserRestController(UserServiceImpl userService){
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public HttpEntity<String> register(@RequestBody User presentUser){

        System.out.println("PRESENT USER!!!!!!!!");
        System.out.println(presentUser);

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
    public HttpEntity<String> login(HttpSession session, @RequestBody User presentUser){


        presentUser.setEmail("");
        if(userService.emailIsValid(presentUser.getUsername())){
            presentUser = new User("", presentUser.getPassword(), presentUser.getEmail());
        }

        User user = userService.findUser(presentUser);

        if (user == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiMessage.loginFailed());
        }

        if (userService.canUserLogIn(presentUser)){
            String token = userService.createTokenFor(user.getId(), session.getId());
            HttpHeaders headers = new HttpHeaders();
            headers.add("setCookie", "sessionToken="+token);
            return ResponseEntity.ok().headers(headers).body(ApiMessage.loginSuccessful());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiMessage.loginFailed());
        }

    }
}
