package com.app.restaurantmanager;

import lombok.Data;

@Data
public class RestaurantManagerDTO {
	
	private String email;

	private String password;

	private String name;

	private String surname;
	
	private Integer restaurant;
}
