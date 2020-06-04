package com.example.springsecurityjwt.security;

import com.example.springsecurityjwt.dto.ApiResponse;
import com.example.springsecurityjwt.dto.AuthUser;
import com.example.springsecurityjwt.dto.ReqSignIn;
import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;


    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Checks if the given email already exists in the database repository or not
     *
     * @return true if the email exists else false
     */
    public Boolean emailAlreadyExists(String email) {
        return userService.existsByEmail(email);
    }

    /**
     * Checks if the given email already exists in the database repository or not
     *
     * @return true if the email exists else false
     */
    public Boolean usernameAlreadyExists(String username) {
        return userService.existsByUsername(username);
    }

    public Authentication authenticateUser(ReqSignIn signIn) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getUsername(),
                signIn.getPassword()));
    }


    public ApiResponse registerUser(AuthUser authUser){
        boolean userExist = usernameAlreadyExists(authUser.getUsername());
        if (userExist){
            return new ApiResponse("Username already exists, Try other ones",false);
        }else {
            User user = userService.createUser(authUser);
            userService.save(user);
            return new ApiResponse("Successfully registered",true);
        }
    }
}
