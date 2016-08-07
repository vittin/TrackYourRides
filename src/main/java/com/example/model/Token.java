package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Mateusz on 2016-08-02.
 */

@Entity
public class Token {
    @Id
    @GeneratedValue
    private long id;
    private long userId;
    private String tokenId;
    private String session;
    private long expiredTime;

    public Token(long userId, String session, String token){
        this.userId = userId;
        this.tokenId = token;
        this.session = session;
    }

    public Token(){};

    public long getUserId() {
        return userId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getSession() {
        return session;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long minutes){
        this.expiredTime = System.currentTimeMillis() + 1000*60*minutes;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenId='" + tokenId + '\'' +
                '}';
    }


}
