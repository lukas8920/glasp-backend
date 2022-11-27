package org.kehrbusch.repositories.queries;

import org.kehrbusch.entities.UserJwtH2;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJwtH2Repository extends CrudRepository<UserJwtH2, Long> {
}
