package com.mathdev.quickbite.mapper;

import com.mathdev.quickbite.dto.UserDTO;
import com.mathdev.quickbite.dto.UserInsertDTO;
import com.mathdev.quickbite.entities.User;

public class UserMapper {
	public static UserDTO toDTO(User entity) {
		return new UserDTO(entity.getId(), entity.getName(), entity.getEmail(), entity.getAddress());
	}

	public static User fromDTO(UserDTO dto) {

		return new User(dto.id(), dto.name(), dto.email(), null, dto.address());
	}

	public static User fromDTOInsert(UserInsertDTO dto) {
		return new User(null, dto.name(), dto.email(), dto.password(), dto.address());
	}

	public static void updateData(User entity, UserDTO newUser) {
		entity.setName(newUser.name());
		entity.setEmail(newUser.email());
		entity.setAddress(newUser.address());
	}
}
