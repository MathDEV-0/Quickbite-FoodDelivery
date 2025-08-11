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

import com.mathdev.quickbite.dto.ProductDTO;
import com.mathdev.quickbite.dto.RestaurantDTO;
import com.mathdev.quickbite.services.RestaurantService;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantResource {

	@Autowired
	RestaurantService restaurantService;

	// GET CONTROLLERS
	@GetMapping
	public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {

		List<RestaurantDTO> temp = restaurantService.findAll();

		return ResponseEntity.ok().body(temp);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable Long id) {
		RestaurantDTO obj = restaurantService.findById(id);

		return ResponseEntity.ok().body(obj);
	}

	@GetMapping("/{id}/products")
	public List<ProductDTO> findProducts(@PathVariable Long id) {
		return restaurantService.findProductsByRestaurant(id);
	}

	// POST CONTROLLERS
	@PostMapping
	public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO dto) {
		RestaurantDTO tempDTO = restaurantService.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.id()).toUri();

		return ResponseEntity.created(uri).body(tempDTO);
	}

	// DELETE CONTROLLERS
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		restaurantService.deleteUser(id);

		return ResponseEntity.noContent().build();
	}

	// PUT CONTROLLERS
	@PutMapping(value = "/{id}")
	public ResponseEntity<RestaurantDTO> updateUser(@PathVariable Long id, @RequestBody RestaurantDTO dto) {
		dto = restaurantService.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}

}
