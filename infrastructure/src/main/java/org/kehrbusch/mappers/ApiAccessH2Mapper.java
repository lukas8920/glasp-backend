package org.kehrbusch.mappers;

import org.kehrbusch.entities.ApiAccess;
import org.kehrbusch.entities.ApiAccessH2;
import org.springframework.stereotype.Component;

@Component
public class ApiAccessH2Mapper {
    public ApiAccessH2 map(ApiAccess apiAccess){
        ApiAccessH2 apiAccessH2 = new ApiAccessH2();
        apiAccessH2.setId(apiAccess.getId());
        apiAccessH2.setUserKey(apiAccess.getUserKey());
        apiAccessH2.setUserPassword(apiAccess.getUserPassword());
        return apiAccessH2;
    }

    public ApiAccess map(ApiAccessH2 apiAccessH2){
        ApiAccess apiAccess = new ApiAccess();
        apiAccess.setId(apiAccessH2.getId());
        apiAccess.setUserKey(apiAccessH2.getUserKey());
        apiAccess.setUserPassword(apiAccessH2.getUserPassword());
        return apiAccess;
    }
}
