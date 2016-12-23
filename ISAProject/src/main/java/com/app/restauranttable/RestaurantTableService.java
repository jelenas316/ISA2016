package com.app.restauranttable;

public interface RestaurantTableService {

	Iterable<RestaurantTable> findAll();
	RestaurantTable findById(Long id);
	RestaurantTable save(RestaurantTable restaurantTable);
	void deleteById(Long id);
	
}
