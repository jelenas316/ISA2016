package com.app.bartender;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.restaurant.Restaurant;

@Repository
public interface BartenderRepository extends CrudRepository<Bartender, String>{
	List<Bartender> findByRestaurant(Restaurant restaurant);
}
