package org.kehrbusch.repositories.impl;

import org.kehrbusch.entities.ApiAccess;
import org.kehrbusch.entities.ApiAccessH2;
import org.kehrbusch.mappers.ApiAccessH2Mapper;
import org.kehrbusch.repositories.ApiAccessRepository;
import org.kehrbusch.repositories.queries.ApiAccessH2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApiAccessRepositoryImpl implements ApiAccessRepository {
    private final ApiAccessH2Repository apiAccessRepository;
    private final ApiAccessH2Mapper apiAccessH2Mapper;

    @Autowired
    public ApiAccessRepositoryImpl(ApiAccessH2Repository apiAccessRepository, ApiAccessH2Mapper apiAccessH2Mapper){
        this.apiAccessRepository = apiAccessRepository;
        this.apiAccessH2Mapper = apiAccessH2Mapper;
    }

    @Override
    public void save(ApiAccess apiAccess) {
        this.apiAccessRepository.save(this.apiAccessH2Mapper.map(apiAccess));
    }

    @Override
    public ApiAccess findById(Long id) {
        Optional<ApiAccessH2> apiAccessH2 = this.apiAccessRepository.findById(id);
        return apiAccessH2.isEmpty() ? null : this.apiAccessH2Mapper.map(apiAccessH2.get());
    }

    @Override
    public ApiAccess findByUserKey(String userKey) {
        ApiAccessH2 apiAccessH2 = this.apiAccessRepository.findByUserKey(userKey);
        return apiAccessH2 == null ? null : this.apiAccessH2Mapper.map(apiAccessH2);
    }

    @Override
    public void updatePw(String pw, Long id) {
        this.apiAccessRepository.update(pw, id);
    }
}
