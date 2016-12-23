package com.app.drink;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DrinkServiceImpl implements DrinkService{

	private final DrinkRepository drinkRepo;
	
	@Autowired
	public DrinkServiceImpl(final DrinkRepository drinkRepo) {
		this.drinkRepo=drinkRepo;
	}
	
	@Override
	public Iterable<Drink> findAll() {
		return drinkRepo.findAll();
	}

	@Override
	public Drink findById(Long id) {
		return drinkRepo.findById(id);
	}

	@Override
	public Drink save(Drink drink) {
		return drinkRepo.save(drink);
	}

	@Override
	public void deleteById(Long id) {
		drinkRepo.deleteById(id);
		
	}

}
