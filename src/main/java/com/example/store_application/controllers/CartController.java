package com.example.store_application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.store_application.controllers.domain.AddCartItemReq;
import com.example.store_application.controllers.domain.RemoveCartItemReq;
import com.example.store_application.dto.CartItemDTO;
import com.example.store_application.services.CartService;
import com.example.store_application.services.UserService;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @GetMapping("/items")
    public ResponseEntity<List<CartItemDTO>> getCartItems() {
        Long cartId = getCartId();
        return ResponseEntity.ok(cartService.getCartItems(cartId));
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<List<CartItemDTO>> addAndUpdateCartItem(@RequestBody AddCartItemReq cartItem) {
        Long cartId = getCartId();
        CartItemDTO cartItemDTO = convertToDTO(cartItem);
        return ResponseEntity.ok(cartService.addAndUpdateCartItem(cartItemDTO, cartId));
    }

    @PostMapping("/remove-from-cart")
    public ResponseEntity<List<CartItemDTO>> removeCartItem(@RequestBody RemoveCartItemReq cartItem) {
        Long cartId = getCartId();
        cartService.removeCartItem(cartItem.getProductId(), cartId);
        return ResponseEntity.ok(cartService.getCartItems(cartId));
    }

    private Long getCartId() {
        Long userId = userService.getCurrentUser();
        Long cartId = cartService.getCartIdByUserId(userId);
        if (cartId == null) {
            cartService.createCart(userId);
            cartId = cartService.getCartIdByUserId(userId);
        }
        return cartId;
    }

    private CartItemDTO convertToDTO(AddCartItemReq addCartItemReq) {
        return CartItemDTO.builder().productId(addCartItemReq.getProductId()).quantity(addCartItemReq.getQuantity())
                .build();
    }

}
