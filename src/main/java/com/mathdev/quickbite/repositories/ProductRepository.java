package com.mathdev.quickbite.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mathdev.quickbite.entities.Product;


public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByRestaurantId(Long restaurantId);

}
