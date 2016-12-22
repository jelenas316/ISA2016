package com.app.restaurant;

public interface RestaurantService {

	Iterable<Restaurant> findAll();
	Restaurant findById(Long id);
	Restaurant save(Restaurant restaurant);
	void deleteById(Long id);
	
}
