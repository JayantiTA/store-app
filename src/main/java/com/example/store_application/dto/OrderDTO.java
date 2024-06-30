package com.example.store_application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {
    private Long id;
    private Long userId;
    private Double totalAmount;
    private String address;
    private String status;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp paidAt;
}
