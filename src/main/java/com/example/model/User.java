package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Created by Mateusz on 2016-07-28.
 */

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    private String login;
    @NotNull
    private String password;
    private String privateKey; //unused
    private String token;
    private Date tokenDate;

    public User(){}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setPrivateKey(String privateKey){
        this.privateKey = privateKey;
    }
    public String getPrivateKey(){
        return this.privateKey;
    }

    public void setToken(String token){
        this.token = token;
    }
    public String getToken(){
        return this.token;
    }

    public void setTokenDate(Date tokenDate) {
        this.tokenDate = tokenDate;
    }
    public Date getTokenDate() {
        return tokenDate;
    }

}
