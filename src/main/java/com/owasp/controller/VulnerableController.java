package com.owasp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vulnerable")
public class VulnerableController {

    // VULNERABILITY 5: Unhandled exception leaks stack trace
    @GetMapping("/error")
    public String triggerError() {
        int[] numbers = {1, 2, 3};
        // This will throw ArrayIndexOutOfBoundsException
        return "The 5th number is: " + numbers[4];
    }
}