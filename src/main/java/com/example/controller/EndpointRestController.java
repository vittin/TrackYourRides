package com.example.controller;

import com.example.model.Session;
import com.example.model.Track;
import com.example.service.AuthService;
import com.example.service.TrackService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Created by Mateusz on 2016-07-28.
 */

@RestController
@RequestMapping("api/")
public class EndpointRestController {

    private final AuthService authService;
    private final TrackService trackService;

    @Autowired
    EndpointRestController(AuthService authService, TrackService trackService){
        this.authService = authService;
        this.trackService = trackService;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public HttpEntity<String> login(@RequestParam String login, @RequestParam String password){

        boolean loginSuccessful = authService.checkCredentials(login, password);

        HttpHeaders headers = new HttpHeaders();
        String token = null;
        if(loginSuccessful){
            token = authService.createNewToken(login);
            headers.add("logged", "true");
        } else {
            headers.add("logged", "false");
        }

        return ResponseEntity.ok().headers(headers).body(token);
    }

    @RequestMapping(value = "getTracks", method = RequestMethod.GET)
    public HttpEntity<List<Track>> getTracks(@RequestHeader Session session){

        if (!isValidSession(session)){
            return notValidSession();
        }

        List<Track> tracks = trackService.getAllTracks(session);

        return ResponseEntity.ok().body(tracks);

    }

    @RequestMapping(value = "addTrack", method = RequestMethod.POST)
    public HttpEntity<String> addTrack(@RequestParam Track track, @RequestHeader Session session){

        if (!isValidSession(session)){
            return notValidSession();
        }

        Long trackId = trackService.addTrack(track, session);

        URI uri = URI.create(""); //todo;
        return ResponseEntity.created(uri).body(null);

    }

    @RequestMapping(value = "deleteTrack/{trackId}", method = RequestMethod.DELETE)
    public HttpEntity<List<Track>> deleteTrack(@PathVariable Long trackId, @RequestHeader Session session){

        if (!isValidSession(session) || isForeignTrack(trackId, session)){
            return notValidSession();
        }

        List<Track> tracks = trackService.deleteTrack(trackId);
        return ResponseEntity.ok().body(tracks);
    }

    @RequestMapping(value = "updateTrack/{trackId}", method = RequestMethod.PUT)
    public HttpEntity<Track> updateTrack(@PathVariable Long trackId, @RequestHeader Session session){

        if (!isValidSession(session) || isForeignTrack(trackId, session)){
            return notValidSession();
        }

        Track track = trackService.updateTrack(trackId);

        URI uri = URI.create("");

        return ResponseEntity.created(uri).body(track);

    }




    private boolean isForeignTrack(Long trackId, Session session) {

        String login = session.getLogin();

        return !(trackService.trackOwner(trackId).equals(login));
    }

    private boolean isValidSession(Session session){

        String login = session.getLogin();
        String token = session.getToken();

        return authService.hasActiveSession(login, token);
    }

    private <T> ResponseEntity<T> notValidSession(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
