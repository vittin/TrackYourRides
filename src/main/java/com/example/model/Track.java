package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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

    private Date date;

    private Weather weather;

    private int temperature;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private User user;


    public Track(double distance, int durationInSeconds, int averageSpeed, int temperature) {
        this.distance = distance;
        this.durationInSeconds = durationInSeconds;
        this.averageSpeed = averageSpeed;
        this.temperature = temperature;
        this.date = new Date();
    }

    public Track(double distance, int durationInSeconds, int averageSpeed, int temperature, Date date){
        this(distance, durationInSeconds, averageSpeed, temperature);
        this.date = date;
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

    public void setUser(User user) {
        this.user = user;
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
