package com.app.bidder;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.restaurant.Restaurant;
@Repository
public interface BidderRepository extends CrudRepository<Bidder, String>{
	List<Bidder> findByRestaurant(Restaurant restaurant);
}
