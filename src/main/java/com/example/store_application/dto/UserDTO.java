package com.example.store_application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
}
