package org.kehrbusch.repositories;

import org.kehrbusch.entities.ApiAccess;

public interface ApiAccessRepository {
    void save(ApiAccess apiAccess);
    ApiAccess findById(Long id);
    ApiAccess findByUserKey(String userKey);
    void updatePw(String pw, Long id);
}
