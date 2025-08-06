package com.mathdev.quickbite.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mathdev.quickbite.dto.OrderDTO;
import com.mathdev.quickbite.dto.OrderItemDTO;
import com.mathdev.quickbite.dto.UserDTO;
import com.mathdev.quickbite.entities.User;
import com.mathdev.quickbite.repositories.OrderRepository;
import com.mathdev.quickbite.repositories.UserRepository;
import com.mathdev.quickbite.entities.Order;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository repo;
	
	@Autowired
	UserRepository userRepository;
	
	//GET SERVICES
	public List<OrderDTO> findAll(){
		List<Order> orders = repo.findAll();
		
		return orders.stream().map(this::toDTO).collect(Collectors.toList()); //fitful for Java < 16 versions
	}
	
	public OrderDTO findById(Long id) {
		Order obj = repo.findById(id).orElseThrow(()->new RuntimeException("User not found!"));
		
		return toDTO(obj);
	}
	
	public List<OrderDTO> findOrdersByUserId(Long userId) {
	    List<Order> list = repo.findByClientId(userId);
	    return list.stream()
	            .map(this::toDTO)
	            .collect(Collectors.toList());
	}
	
	
	//POST SERVICES
	public OrderDTO insert(OrderDTO dto) {
		Order obj = fromDTO(dto);
		obj = repo.save(obj);
		
		return toDTO(obj);
	}
	
	//DELETE SERVICES
	public void deleteUser(Long id) {
		repo.deleteById(id);
	}
	
	//PUT SERVICES
	public OrderDTO update(Long id, OrderDTO newData) {
		Order entity =  repo.getReferenceById(id);
		updateData(entity, newData);
		
		return toDTO(repo.save(entity));
	}
	
	//AUXILIARY METHODS
	private void updateData(Order entity, OrderDTO newData) {
		entity.setMoment(newData.moment());
		if(newData.client() != null) {
			User user = userRepository.findById(newData.client().id()).orElseThrow(()
					->new RuntimeException("User from this order not found"));
			entity.setClient(user);
		}
	}
	
	private Order fromDTO(OrderDTO dto) {
		User user = null;
		if(dto.client() != null) {
			user = new User();
			user.setId(dto.client().id());
		}
		return new Order(dto.id(),dto.moment(),user);
	}
	
	private OrderDTO toDTO(Order entity) {
		UserDTO userDto = null;
		if(entity.getClient() != null) {
			userDto = new UserDTO(entity.getClient().getId(),
					entity.getClient().getName(),
					entity.getClient().getEmail(),
					entity.getClient().getAddress()
					);
		}
		Set<OrderItemDTO> itemsDTO = entity.getItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getSubtotal()))
                .collect(Collectors.toSet());
		
		double total = 0.0;
		
		for(OrderItemDTO item: itemsDTO) {
			total += item.subtotal();
		}
		if(entity.getCoupon()!= null) {
			total = total * (1 - entity.getCoupon().getDiscount() / 100);
			total = Math.round(total * 100) / 100.0;
		}
				
        return new OrderDTO(
                entity.getId(),
                entity.getMoment(),
                userDto,
                itemsDTO,
                total);
    }

	}

