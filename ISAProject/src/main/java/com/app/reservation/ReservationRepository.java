package com.app.reservation;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.restaurant.Restaurant;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long>{
	
	List<Reservation> findByRestaurant(Restaurant restaurant);
	List<Reservation> findByRestaurantId(Long id);
	List<Reservation> findByArrival(Date date);
}
