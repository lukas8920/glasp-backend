package org.kehrbusch.repositories.impl;

import org.kehrbusch.entities.UserJwt;
import org.kehrbusch.entities.UserJwtH2;
import org.kehrbusch.mappers.UserJwtH2Mapper;
import org.kehrbusch.repositories.UserJwtRepository;
import org.kehrbusch.repositories.queries.UserJwtH2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserJwtRepositoryImpl implements UserJwtRepository {
    private final UserJwtH2Mapper mapper;
    private final UserJwtH2Repository userJwtH2Repository;

    @Autowired
    public UserJwtRepositoryImpl(UserJwtH2Mapper mapper, UserJwtH2Repository userJwtH2Repository){
        this.mapper = mapper;
        this.userJwtH2Repository = userJwtH2Repository;
    }

    @Override
    public void save(UserJwt jwt) {

        this.userJwtH2Repository.save(this.mapper.map(jwt));
    }

    @Override
    public UserJwt findJwtById(Long id) {
        UserJwtH2 userJwtH2 = this.userJwtH2Repository.findById(id).orElse(null);
        return userJwtH2 == null ? null : this.mapper.map(userJwtH2);
    }
}
