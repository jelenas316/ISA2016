package com.app.restaurantmanager;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RestaurantManagerServiceImpl implements RestaurantManagerService{

	private final RestaurantManagerRepository restaurantManagerRepo;
	
	@Autowired
	public RestaurantManagerServiceImpl(final RestaurantManagerRepository restaurantManagerRepo) {
		this.restaurantManagerRepo=restaurantManagerRepo;
	}
	
	@Override
	public Iterable<RestaurantManager> findAll() {
		return restaurantManagerRepo.findAll();
	}

	@Override
	public RestaurantManager findOne(String email) {
		return restaurantManagerRepo.findOne(email);
	}

	@Override
	public RestaurantManager save(RestaurantManager restaurantManager) {
		return restaurantManagerRepo.save(restaurantManager);
	}

	@Override
	public void delete(String email) {
		restaurantManagerRepo.delete(email);
	}

}
