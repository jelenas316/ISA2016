package com.app.bartender;

import com.app.restaurant.Restaurant;
import com.app.shift.ShiftDTO;

public interface BartenderService {
	
	Iterable<Bartender> findAll();
	Bartender findOne(String email);
	Bartender save(Bartender bartender);
	void delete(String email);
	Iterable<Bartender> findByRestaurant(Restaurant restaurant);
	void deleteShift(String email, Long shift);
	boolean addShift(ShiftDTO shift);
}
