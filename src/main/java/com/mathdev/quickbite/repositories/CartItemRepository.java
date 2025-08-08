package com.mathdev.quickbite.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mathdev.quickbite.entities.CartItem;
import com.mathdev.quickbite.entities.Product;
import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.entities.pk.CartItemPK;

import jakarta.transaction.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemPK> {
	Optional<CartItem> findByIdCartUserAndIdProduct(User user, Product product);

	List<CartItem> findByIdCartUser(User user);

	@Modifying
	@Transactional
	@Query("DELETE FROM CartItem ci WHERE ci.id.cart.user = :user AND ci.id.product = :product")
	void deleteByUserAndProduct(@Param("user") User user, @Param("product") Product product);

	@Modifying
	@Transactional
	@Query("DELETE FROM CartItem ci WHERE ci.id.cart.user = :user")
	void deleteByUser(@Param("user") User user);
}
