package com.pamihnenkov.notes.repo;

import com.pamihnenkov.notes.domain.AppUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepo extends ReactiveCrudRepository<AppUser, Long> {
    Mono<AppUser> findByUsername(String name);
    Mono<Boolean> existsByUsername(String name);
}
