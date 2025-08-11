package com.mathdev.quickbite.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mathdev.quickbite.entities.Coupon;


public interface CouponRepository extends JpaRepository<Coupon, Long>{
	
}
