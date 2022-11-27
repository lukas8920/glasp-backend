package org.kehrbusch;

import org.kehrbusch.entities.ApiAccess;
import org.kehrbusch.entities.BadRequestException;
import org.kehrbusch.entities.Role;
import org.kehrbusch.entities.User;
import org.kehrbusch.repositories.ApiAccessRepository;
import org.kehrbusch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.logging.Logger;

@Service
public class ApiAccessService {
    private static final Logger logger = Logger.getLogger(ApiAccessService.class.getName());

    private final ApiAccessRepository apiAccessRepository;
    private final UserRepository userRepository;

    @Autowired
    public ApiAccessService(ApiAccessRepository apiAccessRepository, UserRepository userRepository) {
        this.apiAccessRepository = apiAccessRepository;
        this.userRepository = userRepository;
    }

    public ApiAccess getAccessCredentials() throws BadRequestException{
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long user = Long.valueOf(userDetails.getUsername());
        ApiAccess apiAccess = this.apiAccessRepository.findById(user);
        if (apiAccess == null)
            throw new BadRequestException("No such user exists.");
        return apiAccess;
    }

    public void setPw(String password) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long user = Long.valueOf(userDetails.getUsername());
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(6));
        this.apiAccessRepository.updatePw(hash, user);
    }

    public ApiAccess authorizeUser(ApiAccess apiAccess) throws BadRequestException {
        return this.checkAuthorization(user -> !user.getRoles().contains(Role.USER), apiAccess);
    }

    public ApiAccess authorizeAdmin(ApiAccess apiAccess) throws BadRequestException {
        return this.checkAuthorization(user -> !user.getRoles().contains(Role.ADMIN), apiAccess);
    }

    private ApiAccess checkAuthorization(Function<User, Boolean> roleChecker, ApiAccess apiAccess) throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("SERVER"))){
            throw new BadRequestException("Internal Server Error");
        }

        logger.info("Try to find user with key: " + apiAccess.getUserKey());
        ApiAccess tmpAccess = this.apiAccessRepository.findByUserKey(apiAccess.getUserKey());
        if (tmpAccess == null) throw new BadRequestException("Internal Server Error");

        logger.info("Check role.");
        User user = this.userRepository.findById(tmpAccess.getId());
        if (user == null) throw new BadRequestException("Internal Server Error");
        if (roleChecker.apply(user)) throw new BadRequestException("Internal Server Error");

        logger.info("Check password");
        boolean matched = BCrypt.checkpw(apiAccess.getUserPassword(), tmpAccess.getUserPassword());
        if (!matched) throw new BadRequestException("Internal Server Error");

        return tmpAccess;
    }
}
