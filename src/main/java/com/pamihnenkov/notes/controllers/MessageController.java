package com.pamihnenkov.notes.controllers;

import com.pamihnenkov.notes.domain.Message;
import com.pamihnenkov.notes.service.MessageServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@AllArgsConstructor
public class MessageController {

    private final MessageServiceImpl messageServiceImpl;

/**
 * Message processing endpoint. Consumes JSON {"name":"String","message":"String"}
 * Compares name from JSON and JWT. If they are not equals return BAD_REQUEST status code.
 * For correct request do one of the following things:
 * - if message starts with 'history ', tries to convert remaining part of a string to an integer "n". Then
 *      return last "n" messages for user.
 * - if converting to integer fails or message starts not with 'history ' stores message to database and return it as response
 */
    @PostMapping(path = "/process", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public Mono<ResponseEntity<Object>> process(@RequestBody Message message, Authentication authentication){
        if (!message.getName().equals(authentication.getPrincipal())) return Mono.just(ResponseEntity.badRequest().build());
        return Mono.just(message.getMessage())
                    .map(msg -> msg.startsWith("history ") && isInteger(msg.substring(8))
                            ?ResponseEntity.ok(messageServiceImpl.showHistory(message.getName(),Integer.parseInt(msg.substring(8))))
                            :ResponseEntity.ok(messageServiceImpl.addOne(message)));
    }

    private boolean isInteger(String string){
        try{
            Integer.parseInt(string);
            return true;
        }catch (NumberFormatException exception){
            return false;
        }
    }
}
