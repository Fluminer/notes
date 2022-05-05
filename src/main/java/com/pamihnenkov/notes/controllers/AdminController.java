package com.pamihnenkov.notes.controllers;

import com.pamihnenkov.notes.domain.AppUser;
import com.pamihnenkov.notes.domain.AuthRequest;
import com.pamihnenkov.notes.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class AdminController {

    private final UserServiceImpl userServiceImpl;

/**
 * Endpoint for adding new Users. Available only for default user - admin.
 * Consumes JSON {"name":"String","password":"String"}
 */
    @PostMapping(path = "/addUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public Mono<ResponseEntity<Object>> process(@RequestBody AuthRequest authRequest, Authentication authentication){
        if (!(authentication.getPrincipal().equals("admin"))) return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
        return userServiceImpl.checkIfUsernameExists(authRequest.getName())
                .map(isExist->{
                    if (isExist) {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User " + authRequest.getName() + " already exists in system.");
                    } else {
                        return ResponseEntity.status(HttpStatus.CREATED)
                                .body(userServiceImpl.saveUser(new AppUser(authRequest.getName(), authRequest.getPassword())));
                    }
                });
    }
}