package com.example.controller;

import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {

    private AuthService authService;

    @Autowired
    PageController(AuthService authService){
        this.authService = authService;
    }

    @RequestMapping("/")
    public HttpEntity<String> entryPoint(@RequestParam String login, @RequestHeader("token") String token){

        String logged = String.valueOf(authService.hasActiveSession(login, token));
        HttpHeaders headers = new HttpHeaders();
        headers.add("logged", logged);

        return ResponseEntity.ok().headers(headers).body(null);
    }

}
