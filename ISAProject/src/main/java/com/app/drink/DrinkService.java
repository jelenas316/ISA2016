package com.app.drink;

public interface DrinkService {

	Iterable<Drink> findAll();
	Drink findById(Long id);
	Drink save(Drink drink);
	void deleteById(Long id);
	
}
