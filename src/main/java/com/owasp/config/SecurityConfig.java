package com.owasp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF for now (We will discuss REST API CSRF rules in Day 2)
                .csrf(csrf -> csrf.disable())

                // 2. Define Authorization Rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/health", "/h2-console/**").permitAll() // Allow all for now
                        .anyRequest().authenticated()
                )

                // 3. Allow H2 console to be displayed in frames (We will restrict this in Day 3)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

                // 4. Stateless session management (Best practice for REST APIs)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
