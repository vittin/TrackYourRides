package com.example.service;

import com.example.model.Track;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Service
public interface TrackService {

    List<Track> getAllTracks(Long id);

    Long addTrack(Track track);  //id;

    void deleteTrack(Long trackId); //all tracks;

    Track updateTrack(Long trackId, Track track);
}
