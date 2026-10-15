package com.owasp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String role; // e.g., "USER", "ADMIN"

    // VULNERABILITY 1: We will initially save this in plaintext or weak hash
    private String password;
}
