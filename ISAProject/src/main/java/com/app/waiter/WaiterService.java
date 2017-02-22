package com.app.waiter;

import com.app.restaurant.Restaurant;
import com.app.shift.ShiftDTO;

public interface WaiterService {
	
	Iterable<Waiter> findByRestaurant(Restaurant restaurant);
	Waiter findOne(String email);
	Waiter save(Waiter waiter);
	void delete(String email);
	void deleteShift(String email, Long id);
	boolean addShift(ShiftDTO shift);

}
