package com.app.restaurantmanager;

import org.hibernate.validator.constraints.NotBlank;

import com.app.restaurant.Restaurant;

import lombok.Data;
@Data
public class Manager {
	
	private Restaurant restaurant;
	
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String name;

	@NotBlank
	private String surname;
}
