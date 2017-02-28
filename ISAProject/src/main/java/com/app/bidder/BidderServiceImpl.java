package com.app.bidder;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.offers.Offer;
import com.app.restaurant.Restaurant;
@Service
@Transactional
public class BidderServiceImpl implements BidderService{


	private final BidderRepository bidderRepo;
	
	@Autowired
	public BidderServiceImpl(final BidderRepository bidderRepo) {
		this.bidderRepo=bidderRepo;
	}
	
	@Override
	public Iterable<Bidder> findAll() {
		return bidderRepo.findAll();
	}

	@Override
	public Bidder findOne(String email) {
		return bidderRepo.findOne(email);
	}

	@Override
	public Bidder save(Bidder bidder) {
		return bidderRepo.save(bidder);
	}

	@Override
	public void delete(String email) {
		bidderRepo.delete(email);
	}

	@Override
	public Iterable<Bidder> findByRestaurant(Restaurant restaurant) {
		return bidderRepo.findByRestaurant(restaurant);
	}
}
