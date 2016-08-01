package com.example.service;

import com.example.model.Track;

import java.util.List;

/**
 * Created by Mateusz on 2016-07-28.
 */

public interface TrackService {

    List<Track> getAllTracks(String session);

    Long addTrack(Track track, String session);  //id;

    String trackOwner(long trackId);

    List<Track> deleteTrack(long trackId); //all tracks;

    Track updateTrack(Long trackId);
}
