package com.app.drink;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends CrudRepository<Drink, Long>{

	Drink findById(Long id);

	//@Query("select u from User u")
	void deleteById(Long id);
	
}
