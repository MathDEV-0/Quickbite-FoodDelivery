package com.mathdev.quickbite.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.CouponDTO;


import com.mathdev.quickbite.repositories.CouponRepository;
import com.mathdev.quickbite.services.exceptions.DatabaseException;
import com.mathdev.quickbite.services.exceptions.ResourceNotFoundException;
import com.mathdev.quickbite.entities.Coupon;

@Service
public class CouponService {

	@Autowired
	private CouponRepository repo;


	// GET SERVICES
	public List<CouponDTO> findAll() {
		try{
			return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
		}catch (Exception e) {
            throw new DatabaseException("Error retrieving coupons: " + e.getMessage());
        }
	}
	
	public CouponDTO findById(Long id) {
		Coupon obj = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id " + id));
		return toDTO(obj);
	}

	// POST SERVICES
	public CouponDTO insert(CouponDTO dto) {
		try {
			Coupon obj = fromDTO(dto);

			obj = repo.save(obj);
			return toDTO(obj);
		} catch (Exception e) {
			throw new DatabaseException("Error saving coupon: " + e.getMessage());
		}
	}

	// PUT SERVICES
	public CouponDTO update(Long id, CouponDTO newData) {
		Coupon entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id " + id));

		entity.setCode(newData.code());
		entity.setDiscount(newData.discount());
		entity.setExpiry(newData.moment());


		return toDTO(repo.save(entity));
	}

	// DELETE SERVICES
	public void delete(Long id) {
		try {
	        repo.deleteById(id);
	    } catch (Exception e) {
	        throw new DatabaseException("Cannot delete coupon with id " + id + " due to database constraints.");
	    }

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
