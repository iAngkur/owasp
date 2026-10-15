package com.owasp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // VULNERABILITY: Missing Function Level Access Control
    // There is NO check to see if the user is an ADMIN!
    @GetMapping("/reports")
    public String getSensitiveReports() {
        return "CONFIDENTIAL FINANCIAL REPORTS: Q3 Revenue is down 15%...";
    }
}
