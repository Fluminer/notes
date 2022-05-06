package com.pamihnenkov.notes.controllers;

import com.pamihnenkov.notes.config.JwtUtil;
import com.pamihnenkov.notes.domain.AppUser;
import com.pamihnenkov.notes.domain.AuthRequest;

import com.pamihnenkov.notes.domain.AuthResponse;
import com.pamihnenkov.notes.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@WebFluxTest(controllers = AuthController.class,excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
@Import({UserServiceImpl.class,JwtUtil.class})
class AuthControllerTest {

    @Autowired
    private WebTestClient webClient;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void addUser_is_ok() {

        AppUser admin = new AppUser("admin","adminpassword");
        AuthRequest authRequest = new AuthRequest(admin.getUsername(), admin.getPassword());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);

        given(this.userService.findByUsername("admin"))
                .willReturn(Mono.just(new AppUser("admin",passwordEncoder.encode("adminpassword"))));


        this.webClient.mutateWith(csrf()).post().uri("/auth")
                .accept(APPLICATION_JSON)
                .bodyValue(authRequest)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void auth_is_unauthorized() {

        AppUser admin = new AppUser("user","password");
        AuthRequest authRequest = new AuthRequest(admin.getUsername(), admin.getPassword());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);

        given(this.userService.findByUsername("user"))
                .willReturn(Mono.empty());


        this.webClient.mutateWith(csrf()).post().uri("/auth")
                .accept(APPLICATION_JSON)
                .bodyValue(authRequest)
                .exchange()
                .expectStatus().isUnauthorized();
    }


}