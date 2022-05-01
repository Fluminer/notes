package com.pamihnenkov.insidetesttask.repo;

import com.pamihnenkov.insidetesttask.domain.AppUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepo extends ReactiveCrudRepository<AppUser, Long> {
    Mono<AppUser> findByUsername(String name);
}
