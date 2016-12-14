package com.app.bidder;

import com.app.guest.Guest;

public interface BidderService {
	
	Iterable<Bidder> findAll();
	Bidder findOne(String email);
	Bidder save(Bidder bidder);
	void delete(String email);
}
