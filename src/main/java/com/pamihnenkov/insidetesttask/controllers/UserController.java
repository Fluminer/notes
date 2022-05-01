package com.pamihnenkov.insidetesttask.controllers;

import com.pamihnenkov.insidetesttask.config.JwtUtil;
import com.pamihnenkov.insidetesttask.domain.AppUser;
import com.pamihnenkov.insidetesttask.domain.AuthRequest;
import com.pamihnenkov.insidetesttask.domain.AuthResponse;
import com.pamihnenkov.insidetesttask.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @GetMapping("/")
    public Flux<AppUser> list(){
        userService.saveUser(new AppUser("z0dium","password", "ROLE_USER")).subscribe();
        userService.saveUser(new AppUser("someone","123", "ROLE_USER")).subscribe();
        return userService.showAllUsers();
    }

    @PostMapping(value = "/auth",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<ResponseEntity> auth(@RequestBody AuthRequest authRequest) {
        return userService.findByUsername(authRequest.getName())
                        .cast(AppUser.class)
                        .map(appUser -> BCrypt.checkpw(authRequest.getPassword(), appUser.getPassword())
                                ? ResponseEntity.ok(new AuthResponse(jwtUtil.createToken(appUser)))
                                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
