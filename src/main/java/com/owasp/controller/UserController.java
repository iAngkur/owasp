package com.owasp.controller;

import com.google.common.DataProcessor;
import com.owasp.entity.User;
import com.owasp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // SECURE IDOR FIX:
    // We inject the 'Authentication' object to see who is currently logged in.
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id, Authentication authentication) {
        User requestedUser = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        String loggedInUsername = authentication.getName();

        boolean isOwner = requestedUser.getUsername().equals(loggedInUsername);
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isOwner || isAdmin) {
            return requestedUser;
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: You can only view your own profile.");
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/process")
    public String processData(@RequestParam String data) {
        return DataProcessor.processData(data);
    }
}
