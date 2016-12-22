package com.app.reservation;

import java.util.List;

public interface ReservationService {
	Iterable<Reservation> findAll();
	Reservation findOne(Long id);
	Reservation save(Reservation reservation);
	void delete(Long id);
	
	Iterable<Reservation> findPreviousByGuest(String email);
	List<Reservation> findFutureReservationsForGuest(String email);
	
}
