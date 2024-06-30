package com.example.store_application.controllers.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCartItemReq {
    @NotNull
    @JsonProperty("product_id")
    private Long productId;

    @NotNull
    private Integer quantity;
}
