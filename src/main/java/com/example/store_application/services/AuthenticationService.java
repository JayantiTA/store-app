package com.example.store_application.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public String login(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            if (authentication.isAuthenticated()) {
                return "Login successful";
            }
        } catch (AuthenticationException e) {
            return "Login failed: " + e.getMessage();
        }

        return "Login failed";
    }

}
