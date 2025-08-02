package com.mathdev.quickbite.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_restaurants")
public class Restaurant {
	private Long id;
	private String name;
	private String address;
	
	public Restaurant() {
		
	}

	public Restaurant(Long id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
