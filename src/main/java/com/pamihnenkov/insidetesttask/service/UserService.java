package com.pamihnenkov.insidetesttask.service;

import com.pamihnenkov.insidetesttask.domain.AppUser;
import com.pamihnenkov.insidetesttask.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserService implements ReactiveUserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepo.findByUsername(username).cast(UserDetails.class);
    }

    public Mono<AppUser> saveUser(AppUser user){

        return userRepo.save(new AppUser(user.getUsername(),passwordEncoder.encode(user.getPassword()),"ROLE_USER"));
    }

    public Flux<AppUser> showAllUsers(){
        return userRepo.findAll();
    }
}
