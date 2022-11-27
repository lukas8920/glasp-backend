package org.kehrbusch.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ApiAccessH2 {
    @Id
    private Long id;
    private String userKey;
    private String userPassword;

    public ApiAccessH2() {}

    public ApiAccessH2(Long id, String userKey, String userPassword) {
        this.id = id;
        this.userKey = userKey;
        this.userPassword = userPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
