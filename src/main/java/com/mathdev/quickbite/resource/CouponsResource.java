package com.mathdev.quickbite.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mathdev.quickbite.dto.CouponDTO;
import com.mathdev.quickbite.services.CouponService;

@RestController
@RequestMapping(value = "/coupons")
public class CouponsResource {

	@Autowired
	CouponService couponService;

	// GET CONTROLLERS
	@GetMapping
	public ResponseEntity<List<CouponDTO>> getAllCoupons() {
		List<CouponDTO> temp = couponService.findAll();

		return ResponseEntity.ok().body(temp);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CouponDTO> getCouponById(@PathVariable Long id) {
		CouponDTO obj = couponService.findById(id);

		return ResponseEntity.ok().body(obj);
	}

	// POST CONTROLLERS
	@PostMapping
	public ResponseEntity<CouponDTO> createCoupon(@RequestBody CouponDTO dto) {
		CouponDTO tempDTO = couponService.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.id()).toUri();

		return ResponseEntity.created(uri).body(tempDTO);
	}

	// DELETE CONTROLLERS
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
		couponService.delete(id);

		return ResponseEntity.noContent().build();
	}

	// PUT CONTROLLERS
	@PutMapping(value = "/{id}")
	public ResponseEntity<CouponDTO> updateOrder(@PathVariable Long id, @RequestBody CouponDTO dto) {
		dto = couponService.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}

}
