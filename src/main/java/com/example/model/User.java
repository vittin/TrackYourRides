package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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

    User(){}

    public User(String username, String password, String email) {
        this(username, password);
        this.email = email;
    }

    protected User(String username, String password){
        this.username = username;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
