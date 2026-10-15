package com.owasp.controller;

import com.owasp.entity.User;
import com.owasp.service.SecureUserService;
import com.owasp.service.VulnerableUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final VulnerableUserService vulnerableUserService;
    private final SecureUserService secureUserService;

    public AuthController(VulnerableUserService vulnerableUserService, SecureUserService secureUserService) {
        this.vulnerableUserService = vulnerableUserService;
        this.secureUserService = secureUserService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
//        return vulnerableUserService.registerUser(request.username(), request.password(), request.email());
        return secureUserService.registerUser(request.username(), request.password(), request.email());
    }

    public record RegisterRequest(String username, String password, String email) {}
}
