package com.pamihnenkov.notes.controllers;

import com.pamihnenkov.notes.config.JwtUtil;
import com.pamihnenkov.notes.domain.AppUser;
import com.pamihnenkov.notes.domain.AuthRequest;
import com.pamihnenkov.notes.domain.AuthResponse;
import com.pamihnenkov.notes.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserServiceImpl userServiceImpl;
    private final JwtUtil jwtUtil;
/**
 * Authorization endpoint. Consumes JSON {"name":"String","password":"String"}
 * Checks if provided user is stored in database and return JWT {"token":"String"} for authenticated user.
 */
    @PostMapping(value = "/auth",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AuthResponse>> auth(@RequestBody AuthRequest authRequest) {
        return userServiceImpl.findByUsername(authRequest.getName()).cast(AppUser.class)
                            .filter(user -> BCrypt.checkpw(authRequest.getPassword(),user.getPassword()))
                            .map(user -> ResponseEntity.ok(new AuthResponse(jwtUtil.createToken(user))))
                            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
