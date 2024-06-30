package com.example.store_application.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store_application.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);

    Optional<CartItem> findByProductIdAndCartId(Long productId, Long cartId);
}
