package com.mathdev.quickbite.entities.auth;

import com.mathdev.quickbite.entities.enums.Role;

public interface AppUser {
	String getEmail();
	String getPassword();
	Role getRole();
}
