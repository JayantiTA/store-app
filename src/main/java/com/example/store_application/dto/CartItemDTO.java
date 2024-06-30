package com.example.store_application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemDTO {
    private Long id;
    private Long cartId;
    private Long productId;
    private Integer quantity;
    private Double price;
    private Double total;
}
