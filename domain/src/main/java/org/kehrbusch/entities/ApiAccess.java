package org.kehrbusch.entities;

public class ApiAccess {
    private Long id;
    private String userKey;
    private String userPassword;

    public ApiAccess(){}

    public ApiAccess(Long id, String userKey, String userPassword) {
        this.id = id;
        this.userKey = userKey;
        this.userPassword = userPassword;
    }

    public Long getId() {
        return id;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setUserKey(String userKey){
        this.userKey = userKey;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
