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

    private Date date;
    @NotNull
    private Integer distance; //meters
    @NotNull
    private Integer duration; //seconds
    @NotNull
    private Integer averageSpeed; //meters per hour

    private Integer maxSpeed; //meters per hour

    private Weather weather;

    private Integer temperature; //celcious

    @ManyToOne()
    @JoinColumn(name = "userId")
    private User user;


    public Track(Integer distance, Integer duration, Integer averageSpeed, Integer temperature) {
        this.distance = distance;
        this.duration = duration;
        this.averageSpeed = averageSpeed;
        this.temperature = temperature;
        this.date = new Date();
    }

    public Track(Long date, Integer distance, Integer duration, Integer averageSpeed, Integer temperature){
        this(distance, duration, averageSpeed, temperature);
        this.date = new Date(date);
    }

    public Track(){}

    public long getTrackId() {
        return trackId;
    }

    public int getDuration() {
        return duration;
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

    void setDistance(Integer distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackId=" + trackId +
                ", distance=" + distance +
                ", duration=" + duration +
                ", averageSpeed=" + averageSpeed +
                ", weather=" + weather +
                ", temperature=" + temperature +
                '}';
    }
}
