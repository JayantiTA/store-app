package com.example.store_application.controllers.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String role;
}
