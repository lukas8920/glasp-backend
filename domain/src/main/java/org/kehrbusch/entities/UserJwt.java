package org.kehrbusch.entities;

public class UserJwt {
    private Long id;
    private String userToken;
    private String userExpiryDate;

    public UserJwt(){}

    public UserJwt(Long id, String userToken, String userExpiryDate) {
        this.id = id;
        this.userToken = userToken;
        this.userExpiryDate = userExpiryDate;
    }

    public String getUserExpiryDate() {
        return userExpiryDate;
    }

    public void setUserExpiryDate(String userExpiryDate) {
        this.userExpiryDate = userExpiryDate;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
