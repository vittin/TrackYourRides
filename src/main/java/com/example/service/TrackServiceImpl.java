package com.example.service;

import com.example.model.Session;
import com.example.model.Track;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Component
public class TrackServiceImpl implements TrackService {

    @Override
    public List<Track> getAllTracks(Session session) {
        return null;
    }

    @Override
    public Long addTrack(Track track, Session session) {
        return null;
    }

    @Override
    public Track updateTrack(Long trackId){
        return null;
    }

    @Override
    public List<Track> deleteTrack(long trackId) {
        return null;
    }

    @Override
    public String trackOwner(long trackId) {
        return null;
    }
}
