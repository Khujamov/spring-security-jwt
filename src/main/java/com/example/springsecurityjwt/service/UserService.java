package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.dto.AuthUser;
import com.example.springsecurityjwt.entity.User;
import com.example.springsecurityjwt.entity.enums.RoleName;
import com.example.springsecurityjwt.repository.RoleRepository;
import com.example.springsecurityjwt.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * Finds a user in the database by username
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds a user in the database by email
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Find a user in db by id.
     */
    public Optional<User> findById(UUID Id) {
        return userRepository.findById(Id);
    }

    /**
     * Save the user to the database
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Check is the user exists given the email: naturalId
     */
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Check is the user exists given the username: naturalId
     */
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    /**
     * Creates a new user from the registration request
     */
    public User createUser(AuthUser authUser) {
        User newUser = new User();
        newUser.setEmail(authUser.getEmail());
        newUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        newUser.setUsername(authUser.getUsername());
        newUser.addRole(roleService.findByName(RoleName.ROLE_USER));
        return newUser;
    }
}
