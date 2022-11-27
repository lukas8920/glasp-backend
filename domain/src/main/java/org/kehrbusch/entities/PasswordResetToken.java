package org.kehrbusch.entities;

import java.util.Date;

public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24;
    private Long id;
    private String token;
    private User user;
    private Date expiryDate;

    public PasswordResetToken(){
    }

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.id = null;
        long expiration = new Date().getTime() + (EXPIRATION) * 1000;
        this.expiryDate = new Date(expiration);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
