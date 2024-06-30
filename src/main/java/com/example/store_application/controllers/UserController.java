package com.example.store_application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.store_application.controllers.domain.CreateUserReq;
import com.example.store_application.dto.UserDTO;
import com.example.store_application.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid CreateUserReq user) {
        UserDTO userDTO = UserDTO.builder().username(user.getUsername()).email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole()).build();
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

}
