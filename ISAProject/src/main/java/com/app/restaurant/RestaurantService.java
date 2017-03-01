package com.app.restaurant;

import com.app.drinkgrocery.DrinkGrocery;
import com.app.foodStuff.FoodStuff;

public interface RestaurantService {

	Iterable<Restaurant> findAll();
	Restaurant findOne(Long id);
	Restaurant save(Restaurant restaurant);
	void delete(Long id);
	Iterable<FoodStuff> getFoodStuff(Restaurant id);
	Iterable<DrinkGrocery> getDrinkGroceries(Restaurant id);
	Iterable<Restaurant> findRestaurantsWithoutManagers();
}
