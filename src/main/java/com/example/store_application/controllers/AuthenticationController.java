package com.example.store_application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.store_application.controllers.domain.LoginRequest;
import com.example.store_application.controllers.domain.RegisterRequest;
import com.example.store_application.dto.UserDTO;
import com.example.store_application.services.AuthenticationService;
import com.example.store_application.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest request,
            HttpServletResponse response) {
        String respString = authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword(), request,
                response);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(authentication.getName() + " " + respString);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterRequest registerRequest) {
        UserDTO userDTO = UserDTO.builder().username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail()).build();
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

}
