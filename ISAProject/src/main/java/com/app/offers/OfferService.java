package com.app.offers;

import java.util.List;

import com.app.bidder.Bidder;
import com.app.grocerylist.GroceryList;

public interface OfferService {
	
/*	Iterable<Offer> findByRestaurant(Restaurant restaurant);*/
	List<Offer> findByGroceries(GroceryList grocerylist);
	Offer findOne(Long id);
	Offer save(Offer offer);
	void delete(Long id);
	Offer findByBidder(Bidder bidder);
	boolean acceptOffer(Long id);
	boolean rejectOffer(Long id);
	List<Offer> getPastOffers(Bidder b);
	Offer getPastForGroceryList(GroceryList id);
}
