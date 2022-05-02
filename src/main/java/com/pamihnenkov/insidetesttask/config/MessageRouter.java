package com.pamihnenkov.insidetesttask.config;

import com.pamihnenkov.insidetesttask.handlers.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


public class MessageRouter {

    @Bean
    public RouterFunction<ServerResponse> route(MessageHandler messageHandler){

        return RouterFunctions
                .route(RequestPredicates.GET("/messages")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),messageHandler::showMessages);
    }
}
