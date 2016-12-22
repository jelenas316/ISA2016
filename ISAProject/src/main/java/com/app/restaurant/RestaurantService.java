package com.app.restaurant;

public interface RestaurantService {

	Iterable<Restaurant> findAll();
	Restaurant findOne(Long id);
	Restaurant save(Restaurant restaurant);
	void delete(Long id);
	
}
