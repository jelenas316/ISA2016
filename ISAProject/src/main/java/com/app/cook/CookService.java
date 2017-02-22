package com.app.cook;

import com.app.restaurant.Restaurant;
import com.app.shift.ShiftDTO;

public interface CookService {
	
	Iterable<Cook> findAll();
	Cook findOne(String email);
	Cook save(Cook cook);
	void delete(String email);
	void deleteShift(String email, Long id);
	boolean addShift(ShiftDTO shift);
	Iterable<Cook> findByRestaurant(Restaurant restaurant);
}
