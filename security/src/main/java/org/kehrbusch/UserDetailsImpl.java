package org.kehrbusch;

import org.kehrbusch.entities.Role;
import org.kehrbusch.entities.User;
import org.kehrbusch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class UserDetailsImpl implements UserDetailsService {
    private static final Logger logger = Logger.getLogger(UserDetailsImpl.class.getName());

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String profileId) throws UsernameNotFoundException {
        long id;
        try {
            id = Long.parseLong(profileId);
        } catch (NumberFormatException e){
            throw new UsernameNotFoundException("No user found - wrong id format provided");
        }

        final User user = this.userRepository.findById(id);
        if (user == null)
            throw new UsernameNotFoundException("User with id " + id + " not found");

        List<Role> roles = user.getRoles();
        roles.forEach(role -> logger.info(role.toString()));
        List<SimpleGrantedAuthority> authorities = roles.stream().map(role ->
                new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());

        return org.springframework.security.core.userdetails.User
                .withUsername(profileId)
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isEnabled())
                .build();
    }
}
