package com.mathdev.quickbite.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.CartDTO;
import com.mathdev.quickbite.dto.CartItemDTO;
import com.mathdev.quickbite.entities.CartItem;
import com.mathdev.quickbite.entities.Product;
import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.repositories.CartItemRepository;
import com.mathdev.quickbite.repositories.ProductRepository;
import com.mathdev.quickbite.repositories.UserRepository;

@Service
public class CartService {
	
	@Autowired
	CartItemRepository repo;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
    UserRepository userRepository;
	
	@Autowired
	CartItemRepository carItemRepository;
	
	//GET SERVICES
	
	public CartDTO getCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        List<CartItem> items = repo.findByUser(user);

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
	
	

	//POST SERVICES
	
	
	
	//DELETE SERVICES
	
	public void removeProductFromCart(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        carItemRepository.deleteByUserAndProduct(user, product);
    }
	
	//PUT SERVICES
	
}
