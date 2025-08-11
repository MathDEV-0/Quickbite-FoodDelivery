package com.mathdev.quickbite.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.CouponDTO;


import com.mathdev.quickbite.repositories.CouponRepository;

import com.mathdev.quickbite.entities.Coupon;

@Service
public class CouponService {

	@Autowired
	private CouponRepository repo;


	// GET SERVICES
	public List<CouponDTO> findAll() {
		return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	public CouponDTO findById(Long id) {
		Coupon obj = repo.findById(id).orElseThrow(() -> new RuntimeException("Coupon not found!"));
		return toDTO(obj);
	}

	// POST SERVICES
	public CouponDTO insert(CouponDTO dto) {
		Coupon obj = fromDTO(dto);
		obj = repo.save(obj);
		return toDTO(obj);
	}

	// PUT SERVICES
	public CouponDTO update(Long id, CouponDTO newData) {
		Coupon entity = repo.findById(id).orElseThrow(() -> new RuntimeException("Coupon not found"));

		entity.setCode(newData.code());
		entity.setDiscount(newData.discount());
		entity.setExpiry(newData.moment());


		return toDTO(repo.save(entity));
	}

	// DELETE SERVICES
	public void delete(Long id) {
		repo.deleteById(id);
	}

	// AUXILIARY METHODS (CONVERSION)
	private CouponDTO toDTO(Coupon entity) {
		return new CouponDTO(entity.getId(), entity.getCode(), entity.getDiscount(), entity.getExpiry());
	}

	private Coupon fromDTO(CouponDTO dto) {
		Coupon entity = new Coupon();
		entity.setCode(dto.code());
		entity.setDiscount(dto.discount());
		entity.setExpiry(dto.moment());

		return entity;
	}
}
