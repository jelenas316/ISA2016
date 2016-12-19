package com.app.reservation;

public interface ReservationService {
	Iterable<Reservation> findAll();
	Reservation findOne(Long id);
	Reservation save(Reservation reservation);
	void delete(Long id);
	Iterable<Reservation> findPreviousByGuest(String email);
}
