package com.example.store_application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store_application.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
