package com.pamihnenkov.notes.service;

import com.pamihnenkov.notes.domain.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageService {
    Flux<Message> showHistory(String userName, Integer historyCount);
    Mono<Message> addOne(Message message);
}
