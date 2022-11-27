package org.kehrbusch.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class VerificationTokenH2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @OneToOne(targetEntity = UserH2.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserH2 user;
    private Date date;

    public VerificationTokenH2() {}

    public VerificationTokenH2(String token, UserH2 user, Date date) {
        this.token = token;
        this.user = user;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
