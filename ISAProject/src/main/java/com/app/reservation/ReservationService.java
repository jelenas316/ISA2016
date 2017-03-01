package com.app.reservation;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.app.order.Order;
import com.app.restauranttable.RestaurantTable;

public interface ReservationService {
	Iterable<Reservation> findAll();
	Reservation findOne(Long id);
	Reservation save(Reservation reservation);
	void delete(Long id);
	
	Iterable<Reservation> findPreviousByGuest(String email);
	List<Reservation> findFutureReservationsForGuest(String email);
	
	void cancelOrderedDrink(Order order, Long orderedDrinkId);
	void cancelOrderedFood(Order order, Long orderedFoodId);
	void cancelReservation(Reservation reservation, String email);
	
	List<RestaurantTable> findFreeTables(Long restaurantId, Date date, Time time, Integer duration);
	
	List<Visit> reservationsByRestaurant(Long id);
}
