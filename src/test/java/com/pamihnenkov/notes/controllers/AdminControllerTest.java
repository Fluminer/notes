package com.pamihnenkov.notes.controllers;

import com.pamihnenkov.notes.domain.AppUser;
import com.pamihnenkov.notes.domain.AuthRequest;
import com.pamihnenkov.notes.domain.AuthResponse;
import com.pamihnenkov.notes.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AdminController.class)
@Import(UserServiceImpl.class)
class AdminControllerTest {



    @Test
    void process() {


    }
}