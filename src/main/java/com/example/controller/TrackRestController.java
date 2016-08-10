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

import java.net.URI;
import java.util.Map;

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

    private Token token;


    @Autowired
    TrackRestController(TrackService trackService, UserService userService){
        this.trackService = trackService;
        this.userService = userService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public HttpEntity<?> getTracks(@CookieValue(value = "sessionToken", defaultValue = NO_CONTENT) String cookie){

        if(!isAuthorized(cookie)) {
            return notValidSession();
        }

        Map<Long, Track> tracks = trackService.getAllTracks();
        return ResponseEntity.ok().body(tracks);

    }

    @RequestMapping(value = "/{trackId}", method = RequestMethod.GET)
    public HttpEntity<?> getTracks(@CookieValue(value = "sessionToken", defaultValue = NO_CONTENT) String cookie,
                                   @PathVariable long trackId){

        if(!isAuthorized(cookie)) {
            return notValidSession();
        }

        Track track = trackService.getTrack(trackId);
        return ResponseEntity.ok().body(track);

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public HttpEntity<?> addTrack(@CookieValue(value = "sessionToken", defaultValue = NO_CONTENT) String cookie,
                                       @RequestBody Track track){

        if(!isAuthorized(cookie)) {
            return notValidSession();
        }
        System.out.println(track);
        long id = trackService.addTrack(track);
        return ResponseEntity.created(URI.create("/api/tracks/"+id)).body(id);
    }

    @RequestMapping(value = "/{trackId}", method = RequestMethod.DELETE)
    public HttpEntity<?> deleteTrack(@CookieValue(value = "sessionToken", defaultValue = NO_CONTENT) String cookie,
                                     @PathVariable Long trackId){

        if(!isAuthorized(cookie)) {
            return notValidSession();
        }

        trackService.deleteTrack(trackId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @RequestMapping(value = "/{trackId}", method = RequestMethod.PUT)
    public HttpEntity<?> updateTrack(@CookieValue(value = "sessionToken", defaultValue = NO_CONTENT) String cookie,
                                         @PathVariable Long trackId, @PathVariable Track track){

        if(!isAuthorized(cookie)) {
            return notValidSession();
        }

        long id = trackService.updateTrack(trackId, track);

        return ResponseEntity.created(URI.create("/"+id)).body(null);
    }




    private void pullUser(String cookie) {
        User user = userService.findUser(cookie);
        if (user != null){
            token = userService.getToken(cookie);
            trackService.setUser(user);
        }
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
        if (token != null){
            if (cookie.equals(token.getTokenId())){

                if (token.getExpiredTime() > System.currentTimeMillis()){
                    authorized = true;
                }
            }
        }
        return authorized;
    }

    private static ResponseEntity<String> notValidSession(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiMessage.noLoggedUser());
    }
}
