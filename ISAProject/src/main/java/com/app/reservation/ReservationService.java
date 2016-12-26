package com.app.reservation;

import java.util.List;

import com.app.order.Order;

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
	
}
