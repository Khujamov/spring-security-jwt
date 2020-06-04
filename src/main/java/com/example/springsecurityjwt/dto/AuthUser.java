package com.example.springsecurityjwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthUser {

    @NotBlank(message = "UserName shouldn't be null")
    private String username;

    @NotBlank(message = "password shouldn't be null")
    private String password;

    @NotBlank(message = "phone number shouldn't be null")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "email shouldn't be null")
    private String email;
}
