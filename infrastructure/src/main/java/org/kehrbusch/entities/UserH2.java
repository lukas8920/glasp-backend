package org.kehrbusch.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserH2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String mail;
    private String password;
    @ElementCollection(targetClass=Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING) // Possibly optional (I'm not sure) but defaults to ORDINAL.
    @CollectionTable(name="roles")
    @Column(name="role")
    private List<Role> roles;
    private boolean enabled;

    public UserH2(){}

    public UserH2(Long id, String mail, String password, List<Role> roles, boolean enabled) {
        this.mail = mail;
        this.id = id;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
