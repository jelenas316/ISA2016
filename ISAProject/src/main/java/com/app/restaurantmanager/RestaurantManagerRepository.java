package com.app.restaurantmanager;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantManagerRepository extends CrudRepository<RestaurantManager, String>{
	RestaurantManager findByRestaurantId(Long id);
}
