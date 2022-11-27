package org.kehrbusch.controllers;

import org.kehrbusch.ApiAccessService;
import org.kehrbusch.entities.AccessApi;
import org.kehrbusch.entities.ApiAccess;
import org.kehrbusch.entities.BadRequestException;
import org.kehrbusch.entities.MessageResponseApi;
import org.kehrbusch.mappers.AccessApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/web/app")
@CrossOrigin
public class ApiAccessController {
    private static final Logger logger = Logger.getLogger(ApiAccessController.class.getName());

    private final AccessApiMapper accessApiMapper;
    private final ApiAccessService apiAccessService;

    @Autowired
    public ApiAccessController(AccessApiMapper accessApiMapper, ApiAccessService apiAccessService) {
        this.accessApiMapper = accessApiMapper;
        this.apiAccessService = apiAccessService;
    }
    
    @GetMapping("/credentials/user")
    public AccessApi getCredentials() throws BadRequestException {
        ApiAccess apiAccess = this.apiAccessService.getAccessCredentials();
        System.out.println(apiAccess.getUserPassword());
        return this.accessApiMapper.map(apiAccess);
    }

    @PostMapping("/credentials/pw")
    public MessageResponseApi setPw(@RequestBody AccessApi accessApi) {
        this.apiAccessService.setPw(accessApi.getUserPassword());
        return new MessageResponseApi("success");
    }

    @PostMapping("/authorize/user")
    public AccessApi authorizeUser(@RequestBody AccessApi accessApi) throws BadRequestException{
        ApiAccess apiAccess = this.apiAccessService.authorizeUser(this.accessApiMapper.map(accessApi));
        return this.accessApiMapper.map(apiAccess);
    }

    @PostMapping("/authorize/admin")
    public AccessApi authorizeAdmin(@RequestBody AccessApi accessApi) throws BadRequestException {
        ApiAccess apiAccess = this.apiAccessService.authorizeAdmin(this.accessApiMapper.map(accessApi));
        return this.accessApiMapper.map(apiAccess);
    }
}
