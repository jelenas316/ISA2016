package com.app.foodStuff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.restaurant.Restaurant;

public interface FoodStuffRepo extends CrudRepository<FoodStuff, Long>{
	List<FoodStuff> findByRestaurant(Restaurant id);
}
