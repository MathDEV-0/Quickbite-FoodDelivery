package com.mathdev.quickbite.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mathdev.quickbite.dto.AddToCartDTO;
import com.mathdev.quickbite.dto.CartDTO;
import com.mathdev.quickbite.dto.OrderDTO;
import com.mathdev.quickbite.services.CartService;
import com.mathdev.quickbite.services.OrderService;


@RestController
@RequestMapping(value = "/cart")
public class CartResource {

	@Autowired
	OrderService orderService;

	@Autowired
	CartService cartService;

	// GET CONTROLLERS

	@GetMapping
	public ResponseEntity<CartDTO> getCart(@RequestParam Long userId) {
		return ResponseEntity.ok(cartService.getCart(userId));
	}

	// POST CONTROLLERS
	@PostMapping("/{userId}/add")
	public ResponseEntity<Void> addToCart(@PathVariable Long userId, @RequestBody AddToCartDTO request) {

		cartService.addProductToCart(userId, request.productId(), request.quantity());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/{userId}/checkout")
	public ResponseEntity<OrderDTO> checkout(@PathVariable Long userId) {
	    OrderDTO order = cartService.checkout(userId);
	    return ResponseEntity.ok(order);
	}

	// DELETE CONTROLLERS
	@DeleteMapping("/remove/{productId}")
	public ResponseEntity<Void> remove(@RequestParam Long userId, @PathVariable Long productId) {
		cartService.removeProductFromCart(userId, productId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/clear")
	public ResponseEntity<Void> clear(@RequestParam Long userId) {
		cartService.clearCart(userId);
		return ResponseEntity.ok().build();
	}

	// PUT CONTROLLERS
	
}
