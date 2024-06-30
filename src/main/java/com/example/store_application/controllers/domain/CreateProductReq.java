package com.example.store_application.controllers.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductReq {
    @NotBlank
    private String name;

    @NotBlank
    private Double price;

    @NotBlank
    private Integer stock;

    @NotBlank
    @JsonProperty("category_id")
    private Long categoryId;
}
