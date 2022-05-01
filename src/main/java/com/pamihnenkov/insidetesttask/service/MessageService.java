package com.pamihnenkov.insidetesttask.service;


import com.pamihnenkov.insidetesttask.domain.Message;
import com.pamihnenkov.insidetesttask.repo.MessageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class MessageService {

    private final MessageRepo messageRepo;

    public Flux<Message> list(){
        return messageRepo.findAll();
    }

    public Mono<Message> addOne(Message message){
        return messageRepo.save(message);
    }


}
