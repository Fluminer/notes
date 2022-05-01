package com.pamihnenkov.insidetesttask.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Message {

    @Id
    private Long id;
    private String name;
    private String message;

    public Message(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
