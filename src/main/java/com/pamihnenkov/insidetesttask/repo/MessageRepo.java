package com.pamihnenkov.insidetesttask.repo;

import com.pamihnenkov.insidetesttask.domain.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MessageRepo extends ReactiveCrudRepository<Message,Long> {
}
