package com.example.store_application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.store_application.dto.CartDTO;
import com.example.store_application.dto.CartItemDTO;
import com.example.store_application.entities.Cart;
import com.example.store_application.entities.CartItem;
import com.example.store_application.repositories.CartItemRepository;
import com.example.store_application.repositories.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public CartDTO createCart(Long userId) {
        Cart cart = Cart.builder().userId(userId).build();
        cartRepository.save(cart);
        return CartDTO.builder().id(cart.getId()).userId(cart.getUserId()).build();
    }

    public Long getCartIdByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        return cart != null ? cart.getId() : null;
    }

    public List<CartItemDTO> getCartItems(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        return cartItems.stream().map(this::convertToDTO).toList();
    }

    public List<CartItemDTO> addAndUpdateCartItem(CartItemDTO cartItem, Long cartId) {
        CartItem cartItemResp = cartItemRepository
                .findByProductIdAndCartId(cartItem.getProductId(), cartId).orElse(null);
        if (cartItemResp != null) {
            if (cartItem.getQuantity() <= 0) {
                cartItemRepository.delete(cartItemResp);
            } else {
                cartItemResp.setQuantity(cartItem.getQuantity());
                cartItemRepository.save(cartItemResp);
            }
        }
        return getCartItems(cartId);
    }

    public List<CartItemDTO> bulkAddAndUpdateCartItem(List<CartItemDTO> cartItems, Long cartId) {
        List<CartItem> cartItemsResp = cartItems.stream().map(this::convertToEntity).toList();
        for (CartItem cartItem : cartItemsResp) {
            CartItem cartItemResp = cartItemRepository
                    .findByProductIdAndCartId(cartItem.getProductId(), cartId).orElse(null);
            if (cartItemResp != null) {
                if (cartItemResp.getQuantity() + cartItem.getQuantity() <= 0) {
                    cartItemRepository.delete(cartItemResp);
                } else {
                    cartItemResp.setQuantity(cartItemResp.getQuantity() + cartItem.getQuantity());
                    cartItemRepository.save(cartItemResp);
                }
            } else {
                cartItemRepository.save(cartItem);
            }
        }
        return getCartItems(cartId);
    }

    public List<CartItemDTO> removeCartItem(Long productId, Long cartId) {
        CartItem cartItem = cartItemRepository.findByProductIdAndCartId(productId, cartId).orElse(null);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
        }
        return getCartItems(cartId);
    }

    private CartItemDTO convertToDTO(CartItem cartItem) {
        return CartItemDTO.builder().id(cartItem.getId()).cartId(cartItem.getCartId())
                .productId(cartItem.getProductId()).quantity(cartItem.getQuantity()).build();
    }

    private CartItem convertToEntity(CartItemDTO cartItem) {
        return CartItem.builder().id(cartItem.getId()).productId(cartItem.getProductId()).cartId(cartItem.getCartId())
                .quantity(cartItem.getQuantity()).build();
    }

}
