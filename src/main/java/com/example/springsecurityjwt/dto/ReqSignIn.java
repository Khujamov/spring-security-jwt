package com.example.springsecurityjwt.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReqSignIn {

    @NotBlank(message = "UserName shouldn't be null")
    private String username;

    @NotBlank(message = "password shouldn't be null")
    private String password;
}
