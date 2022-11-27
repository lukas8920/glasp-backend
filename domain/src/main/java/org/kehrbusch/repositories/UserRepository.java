package org.kehrbusch.repositories;

import org.kehrbusch.entities.User;

public interface UserRepository {
    boolean existsByUsername(String mail);
    User save(User user);
    User findById(Long id);
    User findByMail(String mail);
    void changePassword(String password, Long id);
}
