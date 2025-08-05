package com.mathdev.quickbite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.mathdev.quickbite.entities.OrderItem;
import com.mathdev.quickbite.entities.pk.OrderItemPK;


public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{
	
}
