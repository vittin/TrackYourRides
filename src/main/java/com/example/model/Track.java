package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Entity
public class Track {

    @Id
    @GeneratedValue
    private long trackId;
    @NotNull
    private double distance;
    @NotNull
    private int durationInSeconds;
    @NotNull
    private int averageSpeed;

    private Weather weather;
    private int temperature;


    public Track(double distance, int durationInSeconds, int averageSpeed, int temperature) {
        this.distance = distance;
        this.durationInSeconds = durationInSeconds;
        this.averageSpeed = averageSpeed;
        this.temperature = temperature;
    }

    Track(){}

    public long getTrackId() {
        return trackId;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public int getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackId=" + trackId +
                ", distance=" + distance +
                ", durationInSeconds=" + durationInSeconds +
                ", averageSpeed=" + averageSpeed +
                ", weather=" + weather +
                ", temperature=" + temperature +
                '}';
    }
}
