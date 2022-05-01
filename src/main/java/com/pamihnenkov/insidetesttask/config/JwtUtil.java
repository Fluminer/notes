package com.pamihnenkov.insidetesttask.config;

import com.pamihnenkov.insidetesttask.domain.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expirationTime;

    public Claims getClaimsFromToken(String authToken){
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(authToken)
                .getBody();
    }

    public String extractUsername(String authToken) {
        return getClaimsFromToken(authToken).getSubject();
    }

    public boolean validateToken(String authToken){
        return getClaimsFromToken(authToken)
                    .getExpiration()
                    .after(new Date());
    }

    public String createToken(AppUser appUser){
        HashMap<String,Object> claims = new HashMap<>();
        Date creationDate = new Date();
        long expirationSeconds = Long.parseLong(expirationTime);
        Date expirationDate = new Date(creationDate.getTime() + expirationSeconds * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(appUser.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}
