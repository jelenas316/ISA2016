package com.app.offers;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.bidder.Bidder;
import com.app.grocerylist.GroceryList;
@Repository
public interface OfferRepository extends CrudRepository<Offer, Long>{
	List<Offer> findByGroceryList(GroceryList id);
	List<Offer> findByBidder(Bidder bidder);
}
