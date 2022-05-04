package com.pamihnenkov.notes.service;


import com.pamihnenkov.notes.domain.Message;
import com.pamihnenkov.notes.repo.MessageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class MessageService {

    private final MessageRepo messageRepo;

    public Flux<Message> list(String userName, Integer historyCount){
        return messageRepo.findByName(userName).takeLast(historyCount);
    }

    public Mono<Message> addOne(Message message){
        return messageRepo.save(message);
    }


}
