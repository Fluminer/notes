package com.pamihnenkov.notes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class AuthResponse {

    private final String token;
}
