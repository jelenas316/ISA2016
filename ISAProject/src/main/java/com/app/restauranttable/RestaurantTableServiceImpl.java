package com.app.restauranttable;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RestaurantTableServiceImpl implements RestaurantTableService{

	private final RestaurantTableRepository restaurantTableRepo;
	
	@Autowired
	public RestaurantTableServiceImpl(final RestaurantTableRepository restaurantTableRepo) {
		this.restaurantTableRepo=restaurantTableRepo;
	}
	
	@Override
	public Iterable<RestaurantTable> findAll() {
		return restaurantTableRepo.findAll();
	}

	@Override
	public RestaurantTable findById(Long id) {
		return restaurantTableRepo.findById(id);
	}

	@Override
	public RestaurantTable save(RestaurantTable restaurantTable) {
		return restaurantTableRepo.save(restaurantTable);
	}

	@Override
	public void deleteById(Long id) {
		restaurantTableRepo.deleteById(id);
		
	}

}
