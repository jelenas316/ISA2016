package com.app.restaurant;

public interface RestaurantService {

	Iterable<Restaurant> findAll();
	Restaurant findOne(String id);
	Restaurant save(Restaurant restaurant);
	void delete(String id);
	
}
