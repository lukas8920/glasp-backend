package org.kehrbusch;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.kehrbusch.entities.Role;
import org.kehrbusch.entities.User;
import org.kehrbusch.entities.UserJwt;
import org.kehrbusch.failures.Publisher;
import org.kehrbusch.repositories.UserJwtRepository;
import org.kehrbusch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 60000;

    private final UserJwtRepository userJwtRepository;
    private final UserRepository userRepository;
    private final UserDetailsImpl userDetails;
    private final Publisher publisher;

    @Autowired
    public JwtTokenProvider(UserDetailsImpl userDetails, UserJwtRepository userJwtRepository,
                            UserRepository userRepository, Publisher publisher){
        this.userJwtRepository = userJwtRepository;
        this.userDetails = userDetails;
        this.userRepository = userRepository;
        this.publisher = publisher;
    }

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createServerToken(Long id, String role){
        Claims claims = Jwts.claims().setSubject(id.toString());
        claims.put("auth", new SimpleGrantedAuthority(role));

        Date date = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() + 6312000000L);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        User user = this.userRepository.findById(id);
        UserJwt userJwt = this.userJwtRepository.findJwtById(id);
        if (user == null && userJwt == null){
            this.userJwtRepository.save(new UserJwt(id, token, expiryDate.toString()));
            this.userRepository.save(new User(id, "", "", List.of(Role.SERVER), true));
            return token;
        }

        return userJwt.getUserToken();
    }

    public String createUserToken(Long id, String role){
        Claims claims = Jwts.claims().setSubject(id.toString());
        claims.put("auth", new SimpleGrantedAuthority(role));

        Date date = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() + validityInMilliseconds);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        this.userJwtRepository.save(new UserJwt(id, token, expiryDate.toString()));

        return token;
    }

    public Authentication getAuthentication(String token) throws InvalidationException {
        UserDetails userDetails;
        try {
            String profileId = validateToken(token);
            userDetails = this.userDetails.loadUserByUsername(profileId);
        } catch (UsernameNotFoundException e){
            throw new InvalidationException("Expired or invalid Jwt Token");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String validateToken(String token) throws InvalidationException{
        try {
            //If validated return id in token
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e){
            throw new InvalidationException("Expired or invalid Jwt Token");
        }
    }

    public String resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
