package org.kehrbusch.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PasswordResetTokenH2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @OneToOne(targetEntity = UserH2.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserH2 user;
    private Date expiryDate;

    public PasswordResetTokenH2(){}

    public PasswordResetTokenH2(Long id, String token, UserH2 user, Date expiryDate) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
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

    public UserH2 getUser() {
        return user;
    }

    public void setUser(UserH2 user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
