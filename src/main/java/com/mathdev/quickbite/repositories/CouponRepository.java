package com.mathdev.quickbite.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mathdev.quickbite.entities.Coupon;


public interface CouponRepository extends JpaRepository<Coupon, Long>{

	List<Coupon> findByClientId(Long userId);
	
}
