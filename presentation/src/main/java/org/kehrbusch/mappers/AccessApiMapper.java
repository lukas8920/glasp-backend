package org.kehrbusch.mappers;

import org.kehrbusch.entities.AccessApi;
import org.kehrbusch.entities.ApiAccess;
import org.springframework.stereotype.Component;

@Component
public class AccessApiMapper {
    public AccessApi map(ApiAccess apiAccess){
        AccessApi accessApi = new AccessApi();
        accessApi.setId(apiAccess.getId());
        accessApi.setUserKey(apiAccess.getUserKey());
        accessApi.setUserPassword(apiAccess.getUserPassword());
        return accessApi;
    }

    public ApiAccess map(AccessApi accessApi){
        ApiAccess apiAccess = new ApiAccess();
        apiAccess.setId(accessApi.getId());
        apiAccess.setUserPassword(accessApi.getUserPassword());
        apiAccess.setUserKey(accessApi.getUserKey());
        return apiAccess;
    }
}
