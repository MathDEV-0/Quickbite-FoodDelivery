package com.mathdev.quickbite.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mathdev.quickbite.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
