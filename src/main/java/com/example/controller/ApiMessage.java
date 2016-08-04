package com.example.controller;

import com.example.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mateusz on 2016-08-01.
 */

public class ApiMessage {

    private static Map<String, String> propertyValueMap;

    static String loginFailed(){

        String description = "Wrong username or password. If you don't have account, try to register first.";

        createBody(description);

        return stringify(propertyValueMap);
    }

    static String loginSuccessful(String previousPage){

        String description = "Login successful. You will be redirect to previous page.";

        createBody(description, previousPage);

        return stringify(propertyValueMap);
    }

    static String loginSuccessful(){

        String description = "Login successful. You will be redirect to main page.";

        createBody(description);

        return stringify(propertyValueMap);
    }

    static String registerFailed() {

        String description = "User already exist! If you have an account, try to log in.";

        createBody(description);

        return stringify(propertyValueMap);

    }

    static String userCreated(User presentUser) {

        String description = "User created. You can log in now.";

        createBody(description, "/login");

        return stringify(propertyValueMap);
    }

    static String incorrectEmail() {

        String description = "Email is invalid. Check your input.";

        createBody(description);

        return stringify(propertyValueMap);


    }

    public static String alreadyLoggedIn() {

        String description = "You are already logged in. Check main page";

        String location = "/tracks";

        createBody(description, location);

        return stringify(propertyValueMap);
    }

    public static String hasActiveSession(boolean activeSession) {

        String state = (activeSession) ? "active" : "disactive";
        String description = "your session is " + state;

        String location = (activeSession) ? "/login" : "/tracks";

        createBody(description, location);

        propertyValueMap.put("state", String.valueOf(activeSession)); //true or false;

        return stringify(propertyValueMap);
    }

    public static String noLoggedUser() {

        String description = "You are not logged in. Please log in first";

        String location = "/login";

        createBody(description, location);

        return stringify(propertyValueMap);
    }

    //PRIVATE SUPPORT METHODS

    private static void createBody(String description, @Nullable String nextPage){
        createBody(description);
        propertyValueMap.put("nextPage", nextPage);
    }

    private static void createBody(String message){
        propertyValueMap = new HashMap<>();
        propertyValueMap.put("message", message);
    }

    private static String stringify(Map<String, ?> map) {
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(propertyValueMap);
        } catch (JsonProcessingException jsonException){
            json = "{'internalServerError': 'JsonProcessingException'}";
        }

        System.out.println(json);
        return json;
    }
}
