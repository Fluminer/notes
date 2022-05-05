package com.pamihnenkov.notes.service;

import com.pamihnenkov.notes.domain.Message;
import com.pamihnenkov.notes.repo.MessageRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;


class MessageService_ShowHistory_Test {

    MessageService messageService;
    @Mock
    MessageRepo messageRepo;

    String username = "username";
    String anotherUsername = "anotherUsername";
    String text1 = "message from username";
    String text2 = "message from anotherUsername";
    String text3 = "another message from username";
    String text4 = "last message from username";
    String text5 = "last message from anotherUsername";
    Message message1 = new Message(1L,username,text1);
    Message message2 = new Message(2L,anotherUsername,text2);
    Message message3 = new Message(3L,username,text3);
    Message message4 = new Message(4L,username,text4);
    Message message5 = new Message(5L,anotherUsername,text5);

    Flux<Message> flux = Flux.just(message1,message2,message3,message4,message5);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageServiceImpl(messageRepo);
    }

    @Test
    void showHistory_lastMessageReturnedForCorrectUser() {

        Mockito.when(messageRepo.findByName(username)).thenReturn(flux.filter(message -> message.getName().equals(username)));
        Flux<Message> userMessages = messageService.showHistory(username,1);

        StepVerifier.create(userMessages)
                    .assertNext(message -> {
                        assertEquals(message.getName(),username);
                        assertEquals(message.getMessage(),text4);
                    })
                    .expectNextCount(0)
                    .verifyComplete();
    }

    @Test
    void showHistory_FewLastMessageReturnedForSameUser() {

        Mockito.when(messageRepo.findByName(username)).thenReturn(flux.filter(message -> message.getName().equals(username)));
        Flux<Message> userMessages = messageService.showHistory(username,2);

        StepVerifier.create(userMessages)
                .assertNext(message -> {
                    assertEquals(message.getName(),username);
                    assertEquals(message.getMessage(),text3);
                })
                .assertNext(message -> {
                    assertEquals(message.getName(),username);
                    assertEquals(message.getMessage(),text4);
                })
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void showHistory_AllMessageReturnedWhenCounterMoreThenMessagesExist() {

        Mockito.when(messageRepo.findByName(username)).thenReturn(flux.filter(message -> message.getName().equals(username)));
        Flux<Message> userMessages = messageService.showHistory(username,100);

        StepVerifier.create(userMessages)
                .expectNextCount(3)
                .verifyComplete();
    }
}