package com.pamihnenkov.notes.controllers;

import com.pamihnenkov.notes.config.JwtUtil;
import com.pamihnenkov.notes.domain.AppUser;
import com.pamihnenkov.notes.domain.AuthRequest;
import com.pamihnenkov.notes.domain.AuthResponse;
import com.pamihnenkov.notes.repo.UserRepo;
import com.pamihnenkov.notes.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AuthController.class)
@Import(UserServiceImpl.class)
class AuthControllerTest {

    @MockBean
    UserServiceImpl userService;

    @MockBean
    JwtUtil jwtUtil;

    @Autowired
    private WebTestClient client;

    @Test
    void auth() {

        AppUser admin = new AppUser("admin","adminpassword");
        AuthRequest authRequest = new AuthRequest(admin.getUsername(), admin.getPassword());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);

        Mockito.when(userService.findByUsername(authRequest.getName())).thenReturn(Mono.just(
                new AppUser(authRequest.getName(), passwordEncoder.encode(authRequest.getPassword()))).cast(UserDetails.class));

        AuthResponse auth = client.mutateWith(csrf()).post().uri("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(authRequest)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AuthResponse.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(auth);
    }
}