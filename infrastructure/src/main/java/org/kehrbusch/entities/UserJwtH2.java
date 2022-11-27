package org.kehrbusch.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserJwtH2 {
    @Id
    private Long id;
    private String userToken;
    private String userExpiryDate;

    public UserJwtH2(){}

    public UserJwtH2(Long id, String userToken, String userExpiryDate) {
        this.id = id;
        this.userToken = userToken;
        this.userExpiryDate = userExpiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public String getUserExpiryDate() {
        return userExpiryDate;
    }

    public void setUserExpiryDate(String userExpiryDate) {
        this.userExpiryDate = userExpiryDate;
    }
}
