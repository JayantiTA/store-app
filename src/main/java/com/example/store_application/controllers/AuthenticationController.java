package com.example.store_application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.store_application.controllers.domain.LoginRequest;
import com.example.store_application.controllers.domain.RegisterRequest;
import com.example.store_application.dto.UserDTO;
import com.example.store_application.services.AuthenticationService;
import com.example.store_application.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        String respString = authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(respString);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterRequest registerRequest) {
        UserDTO userDTO = UserDTO.builder().username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail()).build();
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

}
