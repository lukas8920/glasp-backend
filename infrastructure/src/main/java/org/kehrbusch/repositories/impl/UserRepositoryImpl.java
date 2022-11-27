package org.kehrbusch.repositories.impl;

import org.kehrbusch.entities.User;
import org.kehrbusch.entities.UserH2;
import org.kehrbusch.mappers.UserH2Mapper;
import org.kehrbusch.repositories.UserRepository;
import org.kehrbusch.repositories.queries.UserH2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = Logger.getLogger(UserRepositoryImpl.class.getName());

    private final UserH2Repository userH2Repository;
    private final UserH2Mapper userH2Mapper;

    @Autowired
    public UserRepositoryImpl(UserH2Repository userH2Repository, UserH2Mapper userH2Mapper){
        this.userH2Repository = userH2Repository;
        this.userH2Mapper = userH2Mapper;
    }

    @Override
    public boolean existsByUsername(String mail) {
       List<UserH2> userH2s = this.userH2Repository.findByMail(mail);
        return !userH2s.isEmpty();
    }

    @Override
    public User save(User user) {
        UserH2 userH2 = this.userH2Repository.save(userH2Mapper.map(user));
        return this.userH2Mapper.map(userH2);
    }

    @Override
    public User findById(Long id) {
        UserH2 userH2 = this.userH2Repository.findById(id).orElse(null);
        return userH2 == null ? null : this.userH2Mapper.map(userH2);
    }

    @Override
    public User findByMail(String mail) {
        List<UserH2> userH2s = this.userH2Repository.findByMail(mail);
        return userH2s == null || userH2s.isEmpty() ? null : this.userH2Mapper.map(userH2s.get(0));
    }

    @Override
    public void changePassword(String password, Long id) {
        this.userH2Repository.updatePassword(password, id);
    }
}
