package com.app.restauranttable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantTableRepository extends CrudRepository<RestaurantTable, String>{

	RestaurantTable findById(Long id);

	void deleteById(Long id);
	
}
