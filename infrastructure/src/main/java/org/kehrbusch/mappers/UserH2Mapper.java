package org.kehrbusch.mappers;

import org.kehrbusch.entities.User;
import org.kehrbusch.entities.UserH2;
import org.springframework.stereotype.Component;

@Component
public class UserH2Mapper {
    public UserH2 map(User user){
        UserH2 userH2 = new UserH2();
        userH2.setId(user.getId());
        userH2.setEnabled(user.isEnabled());
        userH2.setMail(user.getMail());
        userH2.setPassword(user.getPassword());
        userH2.setRoles(user.getRoles());
        return userH2;
    }

    public User map(UserH2 userH2){
        User user = new User();
        user.setId(userH2.getId());
        user.setRoles(userH2.getRoles());
        user.setEnabled(userH2.isEnabled());
        user.setMail(userH2.getMail());
        user.setPassword(userH2.getPassword());
        return user;
    }
}
