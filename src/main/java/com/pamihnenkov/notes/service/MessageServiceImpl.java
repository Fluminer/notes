package com.pamihnenkov.notes.service;


import com.pamihnenkov.notes.domain.Message;
import com.pamihnenkov.notes.repo.MessageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Min;

@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepo;

    public Flux<Message> showHistory(String userName, @Min(value = 0, message = "negative value not allowed") Integer historyCount){
        return  messageRepo.findByName(userName).takeLast(historyCount);
    }

    public Mono<Message> addOne(Message message){
        return messageRepo.save(message);
    }


}
