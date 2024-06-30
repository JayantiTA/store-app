package com.example.store_application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    private Long id;
    private String name;
}
