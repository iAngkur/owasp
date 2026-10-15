package com.owasp.service;

import com.owasp.entity.User;
import com.owasp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class VulnerableUserService {

    private final UserRepository userRepository;

    private static final String HARD_CODED_AES_KEY = "MySuperSecretKey123!";

    public VulnerableUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String plainTextPassword, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setRole("USER");

        user.setPassword(hashWithMD5(plainTextPassword));

        return userRepository.save(user);
    }

    private String hashWithMD5(String input) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD%");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
