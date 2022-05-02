package com.pamihnenkov.insidetesttask.controllers;

import com.pamihnenkov.insidetesttask.config.JwtUtil;
import com.pamihnenkov.insidetesttask.domain.Message;
import com.pamihnenkov.insidetesttask.service.MessageService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;


@RestController
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final JwtUtil jwtUtil;

    private boolean isInteger(String string){
        try{
            Integer.parseInt(string);
            return true;
        }catch (NumberFormatException exception){
            return false;
        }
    }

    @PostMapping(path = "/process", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> process(@RequestBody Message message){

        return Mono.just(message.getMessage())
                    .map(msg -> msg.startsWith("history ") && isInteger(msg.substring(8))
                            ?ResponseEntity.ok(messageService.list(message.getName(),Integer.parseInt(msg.substring(8))))
                            :ResponseEntity.ok(messageService.addOne(message)));
    }
}
