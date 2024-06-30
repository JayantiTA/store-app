package com.example.store_application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.store_application.controllers.domain.CreateOrderReq;
import com.example.store_application.controllers.domain.PayOrderReq;
import com.example.store_application.dto.OrderDTO;
import com.example.store_application.services.OrderService;
import com.example.store_application.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('user')")
    @GetMapping("/")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        Long userId = userService.getCurrentUser();
        return ResponseEntity.ok(orderService.getOrderByUserId(userId));
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PreAuthorize("hasRole('user')")
    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid CreateOrderReq createOrderReq) {
        return ResponseEntity.ok(orderService.createOrder(createOrderReq.getAddress(), createOrderReq.getItemIds()));
    }

    @PreAuthorize("hasRole('user')")
    @PostMapping("/pay-order/{orderId}")
    public ResponseEntity<OrderDTO> payOrder(@PathVariable Long orderId, @RequestBody @Valid PayOrderReq payment) {
        return ResponseEntity.ok(orderService.payOrder(orderId, payment.getPaymentMethod()));
    }

}
