package com.pamihnenkov.notes.repo;

import com.pamihnenkov.notes.domain.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MessageRepo extends ReactiveCrudRepository<Message,Long> {
    Flux<Message> findByName (String name);
}
