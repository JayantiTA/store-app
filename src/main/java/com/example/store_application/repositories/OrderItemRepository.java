package com.example.store_application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store_application.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
}
