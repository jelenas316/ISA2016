package com.app.bidder;

import com.app.offers.Offer;
import com.app.restaurant.Restaurant;

public interface BidderService {
	
	Iterable<Bidder> findAll();
	Bidder findOne(String email);
	Bidder save(Bidder bidder);
	void delete(String email);
	Iterable<Bidder> findByRestaurant(Restaurant restaurant);
}
