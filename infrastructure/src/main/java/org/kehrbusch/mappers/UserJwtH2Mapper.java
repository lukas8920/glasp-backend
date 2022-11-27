package org.kehrbusch.mappers;

import org.kehrbusch.entities.UserJwt;
import org.kehrbusch.entities.UserJwtH2;
import org.springframework.stereotype.Component;

@Component
public class UserJwtH2Mapper {
    public UserJwtH2 map(UserJwt userJwt){
        UserJwtH2 userJwtH2 = new UserJwtH2();
        userJwtH2.setId(userJwt.getId());
        userJwtH2.setUserExpiryDate(userJwt.getUserExpiryDate());
        userJwtH2.setUserToken(userJwt.getUserToken());
        return userJwtH2;
    }

    public UserJwt map(UserJwtH2 userJwtH2){
        UserJwt userJwt = new UserJwt();
        userJwt.setId(userJwtH2.getId());
        userJwt.setUserToken(userJwtH2.getUserToken());
        userJwt.setUserExpiryDate(userJwtH2.getUserExpiryDate());
        return userJwt;
    }
}
