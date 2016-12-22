package com.app.food;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends CrudRepository<Food, String>{

	Food findById(Long id);

	void deleteById(Long id);
	
}
