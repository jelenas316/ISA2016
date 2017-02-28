package com.app.reservation;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.restaurant.Restaurant;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long>{
	
	List<Reservation> findByRestaurant(Restaurant restaurant);
	
}
