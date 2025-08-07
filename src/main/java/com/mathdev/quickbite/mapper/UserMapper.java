package com.mathdev.quickbite.mapper;

import com.mathdev.quickbite.dto.UserDTO;
import com.mathdev.quickbite.entities.User;

public class UserMapper {
	public static UserDTO toDTO(User entity) {
		return new UserDTO(entity.getId(),entity.getName(),entity.getEmail(),entity.getAddress());
	}
	public static User fromDTO(UserDTO dto) {
		
        return new User(dto.id(), dto.name(), dto.email(), null, dto.address());
    }
}
