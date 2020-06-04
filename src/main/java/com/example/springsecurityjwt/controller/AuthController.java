package com.example.springsecurityjwt.controller;


import com.example.springsecurityjwt.dto.ApiResponse;
import com.example.springsecurityjwt.dto.AuthUser;
import com.example.springsecurityjwt.dto.JwtResponse;
import com.example.springsecurityjwt.dto.ReqSignIn;
import com.example.springsecurityjwt.security.AuthService;
import com.example.springsecurityjwt.security.JwtTokenProvider;
import com.example.springsecurityjwt.security.UserPrincipal;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider tokenProvider;

    private final AuthService authService;

    public AuthController(JwtTokenProvider tokenProvider, AuthService authService) {
        this.tokenProvider = tokenProvider;
        this.authService = authService;
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody ReqSignIn signIn){
        return getApiToken(signIn);
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody AuthUser authUser){
        ApiResponse response = authService.registerUser(authUser);
        if (response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    private HttpEntity<?> getApiToken(ReqSignIn signIn){
        try {
            Authentication authentication = authService.authenticateUser(signIn);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(userPrincipal);
            return ResponseEntity.ok(new JwtResponse(jwt));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Bad credentials exception");
        }
    }
}
