package com.app.restauranttable;

import java.util.List;

import lombok.Data;
@Data
public class RestaurantTableDTO {
	
	private Long restaurant;
	
	private List<RestaurantTable> tables;
}
