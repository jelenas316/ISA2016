package com.app.waiter;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.restaurant.Restaurant;

@Repository
public interface WaiterRepository extends CrudRepository<Waiter, String> {
	List<Waiter> findByRestaurant(Restaurant restaurant);
}
