package com.mathdev.quickbite.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.CartDTO;
import com.mathdev.quickbite.dto.CartItemDTO;
import com.mathdev.quickbite.dto.OrderDTO;
import com.mathdev.quickbite.entities.Cart;
import com.mathdev.quickbite.entities.CartItem;
import com.mathdev.quickbite.entities.Order;
import com.mathdev.quickbite.entities.OrderItem;
import com.mathdev.quickbite.entities.Product;
import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.entities.enums.OrderStatus;
import com.mathdev.quickbite.mapper.OrderMapper;
import com.mathdev.quickbite.repositories.CartItemRepository;
import com.mathdev.quickbite.repositories.OrderRepository;
import com.mathdev.quickbite.repositories.ProductRepository;
import com.mathdev.quickbite.repositories.UserRepository;
import com.mathdev.quickbite.services.exceptions.DatabaseException;
import com.mathdev.quickbite.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // GET SERVICES

    public CartDTO getCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id" + userId));

        List<CartItem> items = cartItemRepository.findByIdCartUser(user);

        List<CartItemDTO> itemDTOs = items.stream()
                .map(item -> new CartItemDTO(
                        item.getId().getProduct().getId(),
                        item.getId().getProduct().getName(),
                        item.getQuantity(),
                        item.getId().getProduct().getBasePrice(),
                        item.getQuantity() * item.getId().getProduct().getBasePrice()))
                .collect(Collectors.toList());

        double total = itemDTOs.stream().mapToDouble(CartItemDTO::totalPrice).sum();
        int quantity = itemDTOs.stream().mapToInt(CartItemDTO::quantity).sum();

        return new CartDTO(itemDTOs, total, quantity);
    }

    // POST SERVICES

    public void addProductToCart(Long userId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id "+ userId));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with id "+ productId));
        Cart cart = user.getCart();

        CartItem item = cartItemRepository.findByIdCartUserAndIdProduct(user, product)
                .orElse(new CartItem(cart, product, 0, product.getBasePrice()));

        item.setQuantity(item.getQuantity() + quantity);
        cartItemRepository.save(item);
    }

    @Transactional
    public OrderDTO checkout(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id "+ userId));

        Cart cart = user.getCart();
        if (cart == null) {
            throw new RuntimeException("Cart not found for user");
        }

        List<CartItem> cartItems = cartItemRepository.findByIdCartUser(user);
        if (cartItems.isEmpty()) {
        	throw new ResourceNotFoundException("Cart is empty.");
        }

        Order order = new Order();
        order.setClient(user);
        order.setMoment(Instant.now());
        order.setOrderStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getId().getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.getItems().addAll(orderItems);

        orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems);

        return OrderMapper.toDTO(order);
    }

    // DELETE SERVICES

    public void removeProductFromCart(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id "+ userId));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with id "+ productId));

        try {
            cartItemRepository.deleteByUserAndProduct(user, product);
        } catch (Exception e) {
            throw new DatabaseException("Could not delete cart item due to database constraints.");
        }
    }

    public void clearCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id "+ userId));
        cartItemRepository.deleteByUser(user);
    }
}
