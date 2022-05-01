package com.pamihnenkov.insidetesttask.repo;

import com.pamihnenkov.insidetesttask.domain.AppUser;
import com.pamihnenkov.insidetesttask.domain.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MessageRepo extends ReactiveCrudRepository<Message,Long> {
    Flux<Message> findByName (String name);
}
