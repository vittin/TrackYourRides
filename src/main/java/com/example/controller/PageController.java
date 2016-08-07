package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mateusz on 2016-07-31.
 */

@Controller
@RequestMapping(value = "/", method = RequestMethod.GET)
public class PageController {

    @RequestMapping("")
    public String homePage() {
        return "index.html";
    }

    @RequestMapping("login")
    public String login(){
        return "loginPage.html";
    }

    @RequestMapping("register")
    public String register(){
        return "registerPage.html";
    }

    @RequestMapping("tracks")
    public String tracks() {
        return "tracksPage.html";
    }

}
