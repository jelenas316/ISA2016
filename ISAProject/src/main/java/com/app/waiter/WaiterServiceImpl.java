package com.app.waiter;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WaiterServiceImpl implements WaiterService {
	
	private final WaiterRepository waiterRepo;
	
	@Autowired
	public WaiterServiceImpl(final WaiterRepository waiterRepo){
		this.waiterRepo = waiterRepo;
	}
	
	@Override
	public Iterable<Waiter> findAll() {
		return waiterRepo.findAll();
	}
	
	@Override
	public Waiter findOne(String email) {
		return waiterRepo.findOne(email);
	}
	
	@Override
	public Waiter save(Waiter waiter) {
		return waiterRepo.save(waiter);
	}
	
	@Override
	public void delete(String email) {
		waiterRepo.delete(email);
	}

}
