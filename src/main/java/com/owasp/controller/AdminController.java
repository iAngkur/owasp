package com.owasp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // SECURE: Only users with the 'ADMIN' role can access this method.
    // Spring Security automatically prefixes roles with 'ROLE_', so "ADMIN" checks for "ROLE_ADMIN".
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reports")
    public String getSensitiveReports() {
        return "CONFIDENTIAL FINANCIAL REPORTS: Q3 Revenue is down 15%...";
    }
}
