package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Entity
public class Track {

    @GeneratedValue
    private long trackId;

    public long getTrackId() {
        return trackId;
    }
}
