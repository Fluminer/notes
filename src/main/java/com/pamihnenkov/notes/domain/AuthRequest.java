package com.pamihnenkov.notes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class AuthRequest {

    private final String name;
    private final String password;
}
