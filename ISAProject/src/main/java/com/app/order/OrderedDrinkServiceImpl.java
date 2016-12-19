package com.app.order;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderedDrinkServiceImpl implements OrderedDrinkService{

	private final OrderedDrinkRepository orderedDrinkRepo;
	
	@Autowired
	public OrderedDrinkServiceImpl(final OrderedDrinkRepository orderedDrinkRepo) {
		this.orderedDrinkRepo=orderedDrinkRepo;
	}
	
	@Override
	public Iterable<OrderedDrink> findAll() {
		return orderedDrinkRepo.findAll();
	}

	@Override
	public OrderedDrink findOne(Long id) {
		return orderedDrinkRepo.findOne(id);
	}

	@Override
	public OrderedDrink save(OrderedDrink orderedDrink) {
		return orderedDrinkRepo.save(orderedDrink);
	}

	@Override
	public void delete(Long id) {
		orderedDrinkRepo.delete(id);
	}

}
