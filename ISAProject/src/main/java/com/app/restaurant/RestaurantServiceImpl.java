package com.app.restaurant;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService{

	private final RestaurantRepository restaurantRepo;
	
	@Autowired
	public RestaurantServiceImpl(final RestaurantRepository restaurantRepo) {
		this.restaurantRepo=restaurantRepo;
	}
	
	@Override
	public Iterable<Restaurant> findAll() {
		return restaurantRepo.findAll();
	}

	@Override
	public Restaurant findById(Long id) {
		return restaurantRepo.findById(id);
	}

	@Override
	public Restaurant save(Restaurant restaurant) {
		return restaurantRepo.save(restaurant);
	}

	@Override
	public void deleteById(Long id) {
		restaurantRepo.deleteById(id);
	}

}
