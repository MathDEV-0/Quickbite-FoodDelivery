package com.mathdev.quickbite.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mathdev.quickbite.entities.Order;


public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findByClientId(Long clientId);
}
