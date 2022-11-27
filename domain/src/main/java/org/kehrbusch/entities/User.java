package org.kehrbusch.entities;

import java.util.List;

public class User {
    Long id;
    private String mail;
    private String password;
    private List<Role> roles;
    private boolean enabled;

    public User(){}

    public User(Long id, String mail, String password, List<Role> roles, boolean enabled) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
