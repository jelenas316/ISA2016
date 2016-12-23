package com.app.food;

public interface FoodService {

	Iterable<Food> findAll();
	Food findById(Long id);
	Food save(Food food);
	void deleteById(Long id);
	
}
