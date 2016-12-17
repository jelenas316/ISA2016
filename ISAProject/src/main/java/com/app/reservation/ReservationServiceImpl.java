package com.app.reservation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{

	private final ReservationRepository reservationRepo;
	
	@Autowired
	public ReservationServiceImpl(final ReservationRepository reservationRepo) {
		this.reservationRepo=reservationRepo;
	}
	
	@Override
	public Iterable<Reservation> findAll() {
		return reservationRepo.findAll();
	}

	@Override
	public Reservation findOne(Long id) {
		return reservationRepo.findOne(id);
	}

	@Override
	public Reservation save(Reservation reservation) {
		return reservationRepo.save(reservation);
	}

	@Override
	public void delete(Long id) {
		reservationRepo.delete(id);
	}

}
