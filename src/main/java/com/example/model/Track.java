package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @NotNull
    private Integer distance; //meters
    @NotNull
    private Integer duration; //seconds
    @NotNull
    private Integer averageSpeed; //meters per hour

    private Integer maxSpeed; //meters per hour

    private Integer temperature; //celcious

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    //only for tests, can be safety join with second after rewrite tests;
    public Track(Integer distance, Integer duration, Integer averageSpeed, Integer temperature) {
        this.distance = distance;
        this.duration = duration;
        this.averageSpeed = averageSpeed;
        this.temperature = temperature;
        this.date = new Date();
    }

    public Track(Long date, Integer distance, Integer duration, Integer averageSpeed, Integer maxSpeed, Integer temperature){
        this(distance, duration, averageSpeed, temperature);
        this.date = new Date(date);
        this.maxSpeed = maxSpeed;
    }

    public Track(){}

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Integer averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackId=" + trackId +
                ", distance=" + distance +
                ", duration=" + duration +
                ", averageSpeed=" + averageSpeed +
                ", maxSpeed=" + maxSpeed +
                ", temperature=" + temperature +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (trackId != track.trackId) return false;
        if (!date.equals(track.date)) return false;
        if (!distance.equals(track.distance)) return false;
        if (!duration.equals(track.duration)) return false;
        if (averageSpeed != null ? !averageSpeed.equals(track.averageSpeed) : track.averageSpeed != null) return false;
        if (maxSpeed != null ? !maxSpeed.equals(track.maxSpeed) : track.maxSpeed != null) return false;
        return temperature != null ? temperature.equals(track.temperature) : track.temperature == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (trackId ^ (trackId >>> 32));
        result = 31 * result + date.hashCode();
        result = 31 * result + distance.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + (averageSpeed != null ? averageSpeed.hashCode() : 0);
        result = 31 * result + (maxSpeed != null ? maxSpeed.hashCode() : 0);
        result = 31 * result + (temperature != null ? temperature.hashCode() : 0);
        return result;
    }
}
