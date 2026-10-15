package com.owasp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // VULNERABILITY: We are allowing ALL /api/** endpoints to be accessed by ANY authenticated user.
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )

                .httpBasic(httpBasic -> {}) // Enable HTTP Basic Auth for Postman testing
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails alice = User.withDefaultPasswordEncoder()
                .username("alice").password("password123").roles("USER").build();

        UserDetails bob = User.withDefaultPasswordEncoder()
                .username("bob").password("admin123").roles("ADMIN").build();

        return new InMemoryUserDetailsManager(alice, bob);
    }
}
