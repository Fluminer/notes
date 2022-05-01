package com.pamihnenkov.insidetesttask.controllers;

import com.pamihnenkov.insidetesttask.domain.Message;
import com.pamihnenkov.insidetesttask.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/controller")
@AllArgsConstructor
@ResponseBody
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public Flux<Message> list() {
        return messageService.list();
    }

    @PostMapping
    public Mono<Message> add(@RequestBody Message message){
        return messageService.addOne(message);
    }
}
