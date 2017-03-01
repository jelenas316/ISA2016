package com.app.grade;

import lombok.Data;

@Data
public class RestaurantDTO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private String address;
	
	private Integer reiting;
	
	private Integer friendsReiting;
	
}
