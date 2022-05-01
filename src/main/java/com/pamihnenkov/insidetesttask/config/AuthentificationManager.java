package com.pamihnenkov.insidetesttask.config;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AuthentificationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String username;
        try{
            username = jwtUtil.extractUsername(authToken);
        }catch (Exception e){
            username=null;
            System.out.println("Предоставлен неправильный токен");
        }
        if (username != null && jwtUtil.validateToken(authToken)){
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username,null,List.of(new SimpleGrantedAuthority("USER")));
            auth.setAuthenticated(true);
            return Mono.just(auth);
        }else {

            return Mono.empty();

        }
    }
}
