package org.kehrbusch.repositories;

import org.kehrbusch.entities.UserJwt;

public interface UserJwtRepository {
    void save(UserJwt jwt);
    UserJwt findJwtById(Long id);
}
