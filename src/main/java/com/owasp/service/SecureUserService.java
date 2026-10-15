package com.owasp.service;

import com.owasp.entity.User;
import com.owasp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecureUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Inject the secure encoder

    public SecureUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String plainTextPassword, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setRole("USER");

        // SECURE: Hash the password with BCrypt before saving
        user.setPassword(passwordEncoder.encode(plainTextPassword));

        return userRepository.save(user);
    }

}
