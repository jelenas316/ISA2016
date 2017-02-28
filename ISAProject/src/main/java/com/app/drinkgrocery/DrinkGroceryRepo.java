package com.app.drinkgrocery;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.restaurant.Restaurant;

public interface DrinkGroceryRepo extends CrudRepository<DrinkGrocery, Long>{
	List<DrinkGrocery> findByRestaurant(Restaurant id);
}
