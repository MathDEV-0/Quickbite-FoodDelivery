package com.mathdev.quickbite.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mathdev.quickbite.entities.CartItem;
import com.mathdev.quickbite.entities.Product;
import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.entities.pk.CartItemPK;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemPK> {
	Optional<CartItem> findByUserAndProduct(User user, Product product);

	List<CartItem> findByUser(User user);

	void deleteByUserAndProduct(User user, Product product);

	void deleteByUser(User user);
}
