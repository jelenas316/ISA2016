package com.app.order;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderedFoodServiceImpl implements OrderedFoodService{

	private final OrderedFoodRepository orderedFoodRepo;
	
	@Autowired
	public OrderedFoodServiceImpl(final OrderedFoodRepository orderedFoodRepo) {
		this.orderedFoodRepo = orderedFoodRepo;
	}

	@Override
	public Iterable<OrderedFood> findAll() {
		return orderedFoodRepo.findAll();
	}

	@Override
	public OrderedFood findOne(Long id) {
		return orderedFoodRepo.findOne(id);
	}

	@Override
	public OrderedFood save(OrderedFood orderedFood) {
		return orderedFoodRepo.save(orderedFood);
	}

	@Override
	public void delete(Long id) {
		orderedFoodRepo.delete(id);
	}
	
}
