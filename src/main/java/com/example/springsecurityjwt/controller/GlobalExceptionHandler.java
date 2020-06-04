package com.example.springsecurityjwt.controller;


import com.example.springsecurityjwt.dto.ApiResponse;
import com.example.springsecurityjwt.exception.BadRequestException;
import com.example.springsecurityjwt.exception.ConflictException;
import com.example.springsecurityjwt.exception.NotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { BadRequestException.class,
                                NoSuchFieldException.class,
                                NumberFormatException.class,
                                IllegalArgumentException.class})
    public HttpEntity<?> runtime(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(),false));
    }

    @ExceptionHandler(NotFoundException.class)
    public HttpEntity<?> notFoundHandler(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),false));
    }

    @ExceptionHandler(ConflictException.class)
    public HttpEntity<?> conflictHandler(ConflictException e) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),false));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public HttpEntity<?> httpClientErrorHandler(HttpClientErrorException e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),false));
    }
}
