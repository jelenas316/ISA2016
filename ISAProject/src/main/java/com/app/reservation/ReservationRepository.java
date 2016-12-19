package com.app.reservation;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long>{

	@Query("select r from Reservation r where r.arrival < ?1")
	public Iterable<Reservation> findBy(Date today);
	
}
