package com.pamihnenkov.notes.service;

import com.pamihnenkov.notes.domain.AppUser;
import com.pamihnenkov.notes.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
        return userRepo.save(new AppUser(user.getUsername(),passwordEncoder.encode(user.getPassword())));
    }

    public Mono<Boolean> checkIfUsernameExists(String username){
        return userRepo.existsByUsername(username);
    }
}
