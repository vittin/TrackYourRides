package com.example.controller;

import com.example.model.Token;
import com.example.model.Track;
import com.example.model.User;
import com.example.service.TrackService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Mateusz on 2016-07-28.
 */

@RestController
@RequestMapping("api/tracks")
@Scope("session")
public class TrackRestController {

    private static final String NO_CONTENT = "";
    private final TrackService trackService;
    private final UserService userService;

    private final static ResponseEntity AUTHENTICATION_ERROR =
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiMessage.noLoggedUser());

    private Long userId;
    private Token token;


    @Autowired
    TrackRestController(TrackService trackService, UserService userService){
        this.trackService = trackService;
        this.userService = userService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public HttpEntity<?> getTracks(@CookieValue(value = "sessionId", defaultValue = NO_CONTENT) String cookie){

        if(!isAuthorized(cookie)) {
            return AUTHENTICATION_ERROR;
        }


        List<Track> tracks = trackService.getAllTracks(userId);
        return ResponseEntity.ok().body(tracks);

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public HttpEntity<?> addTrack(@CookieValue(value = "sessionId", defaultValue = NO_CONTENT) String cookie,
                                       @RequestParam Track track){

        if(!isAuthorized(cookie)) {
            return AUTHENTICATION_ERROR;
        }

        trackService.addTrack(track);


        return ResponseEntity.status(HttpStatus.CREATED).body(null);

    }

    @RequestMapping(value = "/{trackId}", method = RequestMethod.DELETE)
    public HttpEntity<List<Track>> deleteTrack(@PathVariable Long trackId){


        return ResponseEntity.ok().body(null);
    }

    @RequestMapping(value = "/{trackId}", method = RequestMethod.PUT)
    public HttpEntity<Track> updateTrack(@PathVariable Long trackId){

        return ResponseEntity.status(HttpStatus.CREATED).body(null);

    }


    private void pullUser(String cookie) {
        User user = userService.findUser(cookie);
        if (user != null){
            userId = user.getId();
            token = userService.getTokenFor(userId);
        }
    }

    private <T> ResponseEntity<T> notValidSession(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    private boolean isAuthorized(String cookie) {

        if(cookie.equals(NO_CONTENT)){
            return false;
        }
        if (!isTokenValid(cookie)){
            pullUser(cookie);
        }

        return isTokenValid(cookie);
    }

    private boolean isTokenValid(String cookie){
        boolean authorized = false;
        if (userId != null && token != null){
            if (cookie.equals(token.getToken())){
                if (token.getExpiredTime() < System.currentTimeMillis() % 1000){
                    authorized = true;
                }
            }
        }
        return authorized;
    }
}
