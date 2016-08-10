package com.example.service;

import com.example.model.Track;
import com.example.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Service
public interface TrackService {

    void setUser(User user);

    Map<Long, Track> getAllTracks();

    Track getTrack(long trackId);

    Long addTrack(Track track);  //id;

    void deleteTrack(Long trackId); //all tracks;

    long updateTrack(Long trackId, Track track);
}
