package com.mathdev.quickbite.mapper;


import com.mathdev.quickbite.dto.CouponDTO;
import com.mathdev.quickbite.entities.Coupon;

public class CouponMapper {

	public static CouponDTO toDTO(Coupon entity) {
		return new CouponDTO(entity.getId(), entity.getCode(), entity.getDiscount(), entity.getExpiry());
	}

	public static Coupon fromDTO(CouponDTO dto) {
		Coupon entity = new Coupon();
		entity.setCode(dto.code());
		entity.setDiscount(dto.discount());
		entity.setExpiry(dto.moment());

		return entity;
	}
}
