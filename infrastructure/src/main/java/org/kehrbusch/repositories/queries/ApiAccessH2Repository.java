package org.kehrbusch.repositories.queries;

import org.kehrbusch.entities.ApiAccessH2;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ApiAccessH2Repository extends CrudRepository<ApiAccessH2, Long> {
    public ApiAccessH2 findByUserKey(String userKey);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update ApiAccessH2 apiAccessH2 set apiAccessH2.userPassword =:pw where apiAccessH2.id =:id")
    public void update(@Param("pw") String password, @Param("id") Long id);
}
