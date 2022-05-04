package com.pamihnenkov.notes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthRequest {

    private final String name;
    private final String password;
}
