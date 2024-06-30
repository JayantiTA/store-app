package com.example.store_application.controllers.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCartItemReq {
    @NotBlank
    @JsonProperty("product_id")
    private Long productId;

    @NotBlank
    private Integer quantity;
}
