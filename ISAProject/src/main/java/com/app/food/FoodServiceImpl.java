package com.app.food;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FoodServiceImpl implements FoodService{

	private final FoodRepository foodRepo;
	
	@Autowired
	public FoodServiceImpl(final FoodRepository foodRepo) {
		this.foodRepo=foodRepo;
	}
	
	@Override
	public Iterable<Food> findAll() {
		return foodRepo.findAll();
	}

	@Override
	public Food findById(Long id) {
		return foodRepo.findById(id);
	}

	@Override
	public Food save(Food food) {
		return foodRepo.save(food);
	}

	@Override
	public void deleteById(Long id) {
		foodRepo.deleteById(id);
		
	}

}
