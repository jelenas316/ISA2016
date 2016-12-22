package com.app.restaurantmanager;

public interface RestaurantManagerService {

	Iterable<RestaurantManager> findAll();
	RestaurantManager findOne(String email);
	RestaurantManager save(RestaurantManager restaurantManager);
	void delete(String email);
	
}
