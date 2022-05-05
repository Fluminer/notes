package com.pamihnenkov.notes.service;

import com.pamihnenkov.notes.domain.Message;
import com.pamihnenkov.notes.repo.MessageRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MessageServiceAddOneTest {

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
    Message unsavedMessage = new Message(null,anotherUsername,text5);

    Flux<Message> flux = Flux.just(message1,message2,message3,message4);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageServiceImpl(messageRepo);
    }

    @Test
    void addOne_MessageIsPersistCorrectly(){

        Mockito.when(messageRepo.save(unsavedMessage)).thenReturn(Mono.just(new Message(5L,unsavedMessage.getName(),unsavedMessage.getMessage())));
        Mono<Message> savedMessage = messageService.addOne(unsavedMessage);

        StepVerifier.create(savedMessage)
                .assertNext(message -> {
                    assertNotNull(message.getId());
                    assertEquals(message.getName(),unsavedMessage.getName());
                    assertEquals(message.getMessage(),unsavedMessage.getMessage());
                })
                .verifyComplete();
    }
}
