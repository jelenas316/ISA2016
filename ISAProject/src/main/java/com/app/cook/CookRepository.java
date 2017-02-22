package com.app.cook;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.restaurant.Restaurant;

@Repository
public interface CookRepository extends CrudRepository<Cook, String> {
	List<Cook> findByRestaurant(Restaurant restaurant);
}
