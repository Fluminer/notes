package com.pamihnenkov.insidetesttask.handlers;

import com.pamihnenkov.insidetesttask.domain.Message;
import com.pamihnenkov.insidetesttask.service.MessageService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class MessageHandler {

    private final MessageService messageService;

    public MessageHandler(MessageService messageService) {
        this.messageService = messageService;
    }


    public Mono<ServerResponse> showMessages (ServerRequest request){
        messageService.addOne(new Message("z0dium","privet1"));
        messageService.addOne(new Message("z0dium","privet2"));
        messageService.addOne(new Message("z0dium","privet3"));
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("All messages"));
    }
}
