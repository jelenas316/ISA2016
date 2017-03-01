package com.app.grade;

import java.util.List;

import com.app.guest.Guest;
import com.app.restaurant.Restaurant;

public interface GradeService {

	Iterable<Grade> findAll();
	Grade findOne(Long id);
	Grade save(Grade grade);
	void delete(Long id);
	Iterable<Grade> findByGuest(String email);
	List<Grade> findByRestaurant(Long id);
	List<RestaurantDTO> getRestaurantsDTO(Iterable<Restaurant> allRestaurants,Guest guest);
	GradeDTO findByWaiter(String email);
	GradeDTO findByFood(Long id);
	GradeDTO findRestaurantGrade(Long id);
}
