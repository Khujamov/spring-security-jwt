package com.example.springsecurityjwt.dto;

import lombok.Data;

@Data
public class JwtResponse {
    private String accessToken;
    private final String tokenType = "Bearer ";

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
