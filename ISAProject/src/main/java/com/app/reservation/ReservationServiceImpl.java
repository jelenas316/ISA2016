package com.app.reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.guest.Guest;

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

	@Override
	public Iterable<Reservation> findPreviousByGuest(String email) {
		return getByEmail(email, reservationRepo.findBy(new Date()));
	}
	
	/**
	 * Method that returns all reservations where guest was.
	 * 
	 * @param email - email of the guest
	 * @param reservations - reservations for search
	 */
	public List<Reservation> getByEmail(String email,Iterable<Reservation> reservations){
		List<Reservation> returnValue=new ArrayList<>();
		for(Reservation reservation : reservations){
			for(Guest guest : reservation.getGuests()){
				if(guest.getEmail().equals(email))
					returnValue.add(reservation);
			}
		}
		
		return returnValue;
	}

}
