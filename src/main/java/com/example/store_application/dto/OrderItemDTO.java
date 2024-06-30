package com.example.store_application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double price;
}
