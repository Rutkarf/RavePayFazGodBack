package org.example.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.example.exception.AccountExistsException;
import org.example.repository.OwnerRepository;
import org.example.repository.security.OwnerRepositoryModel;
import org.example.services.security.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class JwtUserServiceImpl implements JwtUserService {
    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final String signingKey;
    public JwtUserServiceImpl(OwnerRepository ownerRepository, PasswordEncoder passwordEncoder, @Value("${jwt.signing.key}") String signingKey) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.signingKey = signingKey;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        OwnerRepositoryModel owner = ownerRepository.findByLogin(username);
        if (owner == null) {
            throw new UsernameNotFoundException("The owner could not be found");
        }
        return owner;
    }

    // USED FOR REGISTRATION

    @Override
    public Authentication authenticate(String username, String password) throws Exception {
        Authentication authentication = new
                UsernamePasswordAuthenticationToken(username, password);
        return authenticationConfiguration.getAuthenticationManager().authenticate(authentication);
    }

    @Override
    public UserDetails save(String username, String password) throws AccountExistsException {
        return null;
    }


    @Override
    public OwnerRepositoryModel save(String username, String password, String pseudo, String location, String description, String Adresse, MultipartFile profil_picture) throws AccountExistsException {
        UserDetails existingUser = ownerRepository.findByLogin(username);
        if (existingUser != null) {
            throw new AccountExistsException();
        }
        OwnerRepositoryModel owner = new OwnerRepositoryModel();
        owner.setEmail(username);
        owner.setPassword(passwordEncoder.encode(password));
        ownerRepository.save(owner);
        return owner;

    }

    //USED FOR AUTHENTIFICATION

    @Override
    public UserDetails getUserFromJwt(String jwt) {
        String username = getUsernameFromToken(jwt);
        return loadUserByUsername(username);
    }

    private String getUsernameFromToken(String token) {
        System.out.println(signingKey);
        Claims claims =
                Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


    @Override
    public String generateJwtForUser(UserDetails user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600 * 1000);
        return
                Jwts.builder().setSubject(user.getUsername()).setIssuedAt(now).setExpiration(expiryDate)
                        .signWith(SignatureAlgorithm.HS512, signingKey)
                        .compact();
    }



}
