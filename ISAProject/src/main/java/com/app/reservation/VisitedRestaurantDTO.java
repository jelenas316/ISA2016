package com.app.reservation;

import java.util.Date;
import java.util.List;

import com.app.grade.RestaurantDTO;
import com.app.guest.Guest;

import lombok.Data;

@Data
public class VisitedRestaurantDTO {

	private Long id;
	
	private RestaurantDTO restaurant;
	
	private Date arrival;
	
	private List<Guest> friends;
	
}
