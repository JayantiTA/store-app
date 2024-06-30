package com.example.store_application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDTO {
    private Long id;
    private Long userId;
}
