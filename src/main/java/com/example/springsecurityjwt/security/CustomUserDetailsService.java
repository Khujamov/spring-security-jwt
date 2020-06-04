package com.example.springsecurityjwt.security;

import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> currentUser = userRepository.findByUsername(username);
        return currentUser.map(UserPrincipal::new).orElseThrow(()-> new UsernameNotFoundException("Couldn't find a matching  username in the database for " + username));
    }

    public UserDetails loadUserById(UUID id){
        Optional<User> currentUser = userRepository.findById(id);
        return currentUser.map(UserPrincipal::new).orElseThrow(()-> new UsernameNotFoundException("Couldn't find a matching user id in the database for " + id));
    }
}
