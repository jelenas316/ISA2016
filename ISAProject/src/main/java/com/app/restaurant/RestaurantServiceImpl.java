package com.app.restaurant;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.drinkgrocery.DrinkGrocery;
import com.app.drinkgrocery.DrinkGroceryRepo;
import com.app.foodStuff.FoodStuff;
import com.app.foodStuff.FoodStuffRepo;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService{

	private final RestaurantRepository restaurantRepo;
	private final FoodStuffRepo foodStuffRepo;
	private final DrinkGroceryRepo drinkGroceryRepo;
	
	@Autowired
	public RestaurantServiceImpl(final RestaurantRepository restaurantRepo, final FoodStuffRepo foodStuffRepo, final DrinkGroceryRepo drinkGroceryRepo) {
		this.restaurantRepo=restaurantRepo;
		this.foodStuffRepo = foodStuffRepo;
		this.drinkGroceryRepo = drinkGroceryRepo;
	}
	
	@Override
	public Iterable<Restaurant> findAll() {
		return restaurantRepo.findAll();
	}

	@Override
	public Restaurant findOne(Long id) {
		return restaurantRepo.findOne(id);
	}

	@Override
	public Restaurant save(Restaurant restaurant) {
		return restaurantRepo.save(restaurant);
	}

	@Override
	public void delete(Long id) {
		restaurantRepo.delete(id);
	}

	@Override
	public Iterable<FoodStuff> getFoodStuff(Restaurant id) {
		return foodStuffRepo.findByRestaurant(id);
	}

	@Override
	public Iterable<DrinkGrocery> getDrinkGroceries(Restaurant id) {
		return drinkGroceryRepo.findByRestaurant(id);
	}
}
