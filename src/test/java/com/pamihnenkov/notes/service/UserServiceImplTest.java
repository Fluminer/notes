package com.pamihnenkov.notes.service;

import com.pamihnenkov.notes.domain.AppUser;
import com.pamihnenkov.notes.repo.UserRepo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.*;

public class UserServiceImplTest {

    UserServiceImpl userService;
    @Mock
    UserRepo userRepo;
    @Mock
    PasswordEncoder passwordEncoder;

    AppUser admin = new AppUser("admin","adminPassword");
    AppUser user = new AppUser("user","password");
    Flux<AppUser> flux;
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepo,passwordEncoder);
        flux = Flux.just(admin,user);
    }

    @Test
    public void findByUsername_test() {
        Mockito.when(userRepo.findByUsername("admin")).thenReturn(flux.filter(appUser -> appUser.getUsername().equals("admin")).single());
        Mono<AppUser> foundedUser = userService.findByUsername("admin").cast(AppUser.class);
        StepVerifier.create(foundedUser)
                .assertNext(appUser -> assertEquals(appUser,admin))
                .verifyComplete();
    }

    @Test
    public void saveUser() {
        AppUser newUser = new AppUser("newUser","pass");
        Mockito.when(userRepo.save(newUser)).thenReturn(Mono.just(newUser));
        Mono<AppUser> savedUser = userService.saveUser(newUser);
        StepVerifier.create(savedUser)
                .assertNext(appUser -> assertEquals(appUser,newUser));
    }

    @Test
    public void checkIfUsernameExists() {
        String username = "admin";
        Mockito.when(userRepo.existsByUsername(username))
                .thenReturn(Mono.just(Boolean.TRUE));

        Mono<Boolean> isUserExist = userService.checkIfUsernameExists("admin");
        StepVerifier.create(isUserExist)
                .assertNext(Assert::assertTrue);
    }
}