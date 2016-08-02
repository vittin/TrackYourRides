package com.example.controller;

import com.example.model.Token;
import com.example.model.Track;
import com.example.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.List;

/**
 * Created by Mateusz on 2016-07-28.
 */

@RestController
@RequestMapping("api/tracks")
@Scope("session")
public class TrackRestController {

    private final TrackService trackService;
    private String session = "";

    @Autowired
    TrackRestController(TrackService trackService){
        this.trackService = trackService;
    }

    @RequestMapping(value = "tracks", method = RequestMethod.GET)
    public HttpEntity<List<Track>> getTracks(HttpHeaders headers){


        if (!isValidSession(session)){
            return notValidSession();
        }

        List<Track> tracks = trackService.getAllTracks(session);

        return ResponseEntity.ok().body(tracks);

    }

    @RequestMapping(value = "tracks", method = RequestMethod.POST)
    public HttpEntity<String> addTrack(@RequestParam Track track){

        if (!isValidSession(session)){
            return notValidSession();
        }

        Long trackId = trackService.addTrack(track, session);

        URI uri = URI.create(""); //todo;
        return ResponseEntity.created(uri).body(null);

    }

    @RequestMapping(value = "tracks/{trackId}", method = RequestMethod.DELETE)
    public HttpEntity<List<Track>> deleteTrack(@PathVariable Long trackId){

        if (!isValidSession(session) || isForeignTrack(trackId, session)){
            return notValidSession();
        }

        List<Track> tracks = trackService.deleteTrack(trackId);
        return ResponseEntity.ok().body(tracks);
    }

    @RequestMapping(value = "tracks/{trackId}", method = RequestMethod.PUT)
    public HttpEntity<Track> updateTrack(@PathVariable Long trackId){

        if (!isValidSession(session) || isForeignTrack(trackId, session)){
            return notValidSession();
        }

        Track track = trackService.updateTrack(trackId);

        URI uri = URI.create("");

        return ResponseEntity.created(uri).body(track);

    }




    private boolean isForeignTrack(Long trackId, String session) {

        return true;
    }

    private boolean isValidSession(HttpSession session, Token token){
        return false;
    }

    private boolean isValidSession(String session){
        return false;
    } //todo: remove it;

    private <T> ResponseEntity<T> notValidSession(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
