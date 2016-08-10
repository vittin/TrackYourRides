package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    @Column(unique = true)
    private String email;

    @OneToMany(targetEntity = Track.class, cascade=CascadeType.ALL)
    private Map<Long, Track> tracks;

    public User(){}

    public User(String username, String password, String email) {
        this(username, password);
        this.email = email;
    }

    protected User(String username, String password){
        this.username = username;
        this.password = password;
        tracks = new HashMap<>();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<Long, Track> getTracks() {
        return tracks;
    }

    public long addTrack(Track track){
        tracks.put(track.getTrackId(), track);
        return track.getTrackId();
    }

    public void removeTrack(Track track){
        if (tracks.containsKey(track.getTrackId())){
            tracks.remove(track.getTrackId(), track);
        }
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
