package com.example.store_application.controllers.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemoveCartItemReq {
    @NotBlank
    private Long productId;
}
