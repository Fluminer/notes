package com.pamihnenkov.insidetesttask.domain;

public class AuthRequest {

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public AuthRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
