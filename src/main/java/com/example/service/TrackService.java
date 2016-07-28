package com.example.service;

import com.example.model.Session;
import com.example.model.Track;

import java.util.List;

/**
 * Created by Mateusz on 2016-07-28.
 */

public interface TrackService {

    List<Track> getAllTracks(Session session);

    Long addTrack(Track track, Session session);  //id;

    String trackOwner(long trackId);

    List<Track> deleteTrack(long trackId); //all tracks;

    Track updateTrack(Long trackId);
}
