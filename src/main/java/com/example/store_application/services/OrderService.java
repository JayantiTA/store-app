package com.example.store_application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.store_application.dto.CartItemDTO;
import com.example.store_application.dto.OrderDTO;
import com.example.store_application.dto.ProductDTO;
import com.example.store_application.entities.Order;
import com.example.store_application.entities.OrderItem;
import com.example.store_application.entities.Payment;
import com.example.store_application.repositories.OrderItemRepository;
import com.example.store_application.repositories.OrderRepository;
import com.example.store_application.repositories.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
            PaymentRepository paymentRepository, ProductService productService, CartService cartService,
            UserService userService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.productService = productService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @Transactional
    public OrderDTO createOrder(String address, List<Long> cartItemIds) {
        OrderDTO order = OrderDTO.builder().userId(userService.getCurrentUser()).address(address).build();
        List<CartItemDTO> cartItems = cartItemIds.stream().map(cartService::getCartItemById)
                .collect(Collectors.toList());
        Double total_amount = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        // Check if the product is out of stock
        for (CartItemDTO cartItem : cartItems) {
            ProductDTO product = productService.getProductById(cartItem.getProductId());
            OrderItem orderItem = OrderItem.builder().orderId(order.getId()).productId(cartItem.getProductId())
                    .quantity(cartItem.getQuantity()).price(product.getPrice()).build();
            orderItems.add(orderItem);
            total_amount += product.getPrice() * cartItem.getQuantity();

            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Product out of stock");
            }
        }

        // Create order
        order.setTotalAmount(total_amount);
        order.setStatus("unpaid");

        Order orderEntity = convertToEntity(order);
        Order orderResp = orderRepository.save(orderEntity);

        // Create order items
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(orderResp.getId());
            orderItemRepository.save(orderItem);
        }

        // remove cart items
        for (Long cartItemId : cartItemIds) {
            cartService.removeCartItemById(cartItemId);
        }

        return convertToDTO(orderResp);
    }

    public OrderDTO getOrderById(Long id) {
        OrderDTO order = orderRepository.findById(id).map(this::convertToDTO).orElse(null);
        if (order != null && order.getStatus() == "paid") {
            Payment payment = paymentRepository.findByOrderId(id).orElse(null);
            order.setPaidAt(payment.getCreatedAt());
        }
        return order;
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<OrderDTO> orders = orderRepository.findByUserId(userId).stream().map(this::convertToDTO)
                .collect(Collectors.toList());
        for (OrderDTO order : orders) {
            if (order.getStatus() == "paid") {
                Payment payment = paymentRepository.findByOrderId(order.getId()).orElse(null);
                order.setPaidAt(payment.getCreatedAt());
            }
        }
        return orders;
    }

    public OrderDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElse(null);
        order.setStatus(status);
        Order orderResp = orderRepository.save(order);
        return convertToDTO(orderResp);
    }

    public OrderDTO updateOrderAddress(Long id, String address) {
        Order order = orderRepository.findById(id).orElse(null);
        order.setStatus(address);
        Order orderResp = orderRepository.save(order);
        return convertToDTO(orderResp);
    }

    @Transactional
    public OrderDTO payOrder(Long id, String paymentMethod) {
        Payment payment = Payment.builder().orderId(id).paymentMethod(paymentMethod).build();
        Order order = orderRepository.findById(id).orElse(null);

        Payment paymentResp = paymentRepository.save(payment);

        order.setStatus("paid");
        Order orderResp = orderRepository.save(order);

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(id);

        for (OrderItem orderItem : orderItems) {
            ProductDTO product = productService.getProductById(orderItem.getProductId());
            productService.updateProductQuantity(product.getId(), product.getStock() - orderItem.getQuantity());
        }

        OrderDTO orderRespDTO = convertToDTO(orderResp);
        orderRespDTO.setPaidAt(paymentResp.getCreatedAt());

        return orderRespDTO;
    }

    public List<OrderDTO> getOrderByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    private OrderDTO convertToDTO(Order order) {
        return OrderDTO.builder().id(order.getId()).userId(order.getUserId())
                .totalAmount(order.getTotalAmount()).address(order.getAddress()).status(order.getStatus())
                .createdAt(order.getCreatedAt()).build();
    }

    private Order convertToEntity(OrderDTO order) {
        return Order.builder().id(order.getId()).userId(order.getUserId())
                .totalAmount(order.getTotalAmount()).address(order.getAddress()).status(order.getStatus())
                .createdAt(order.getCreatedAt()).build();
    }

}
