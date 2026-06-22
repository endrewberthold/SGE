package org.sge.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.sge.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user){

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .expiration(new Date(
                        System.currentTimeMillis()
                        +86400000
                ))
                .signWith(getSignKey())
                .compact();
    }

    public String extractUsername(String token){

        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ){
        String username = extractUsername(token);

        return username.equals(userDetails.getUsername());
    }

    private SecretKey getSignKey(){
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }
}
