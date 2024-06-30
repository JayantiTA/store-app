package com.example.store_application.controllers.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserReq {
    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String role;
}
