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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;


@RestController
@AllArgsConstructor
@ResponseBody
public class MessageController {

    private final MessageService messageService;
    private final JwtUtil jwtUtil;


    @PostMapping(path = "/process", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> process(ServerRequest serverRequest, @RequestBody Message message){

        String authHeader = serverRequest.headers().firstHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer_")){
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)){
                Claims claims = jwtUtil.getClaimsFromToken(token);
                String user = claims.getSubject();
                if (message.getName().equals(user)){
                    if (message.getMessage().startsWith("history ")){
                        return Mono.just(ResponseEntity.ok().body(messageService.list(user,Integer.parseInt(message.getMessage().substring(8)))));
                    }else return Mono.just(ResponseEntity.ok().body(messageService.addOne(message)));
                }
            }return Mono.just(new ResponseEntity<>("Provided JWT is not valid.",HttpStatus.UNAUTHORIZED));
        }return Mono.just(new ResponseEntity<>("Wrong authorization token.",HttpStatus.NOT_ACCEPTABLE));
    }
}
