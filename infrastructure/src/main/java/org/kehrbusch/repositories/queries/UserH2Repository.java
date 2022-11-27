package org.kehrbusch.repositories.queries;

import org.kehrbusch.entities.UserH2;
import org.kehrbusch.entities.UserJwtH2;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserH2Repository extends CrudRepository<UserH2, Long> {
    @Query("SELECT u FROM UserH2 u WHERE u.mail  = :mail")
    List<UserH2> findByMail(@Param("mail") String mail);
    @Modifying
    @Transactional
    @Query("update UserH2 user set user.password = :password where user.id = :id")
    void updatePassword(@Param("password") String password, @Param("id") Long id);
}
