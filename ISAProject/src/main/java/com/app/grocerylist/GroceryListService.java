package com.app.grocerylist;

import java.util.List;

import com.app.restaurant.Restaurant;

public interface GroceryListService {
	
	GroceryList findByRestaurantActive(Restaurant restaurant);
	List<GroceryList> findByRestaurant(Restaurant restaurant);
	GroceryList findOne(Long id);
	GroceryList save(GroceryList groceryList);
	void delete(Long id);
}
