package com.app.cook;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CookServiceImpl implements CookService {
	
	private final CookRepository cookRepo;
	
	@Autowired
	public CookServiceImpl(final CookRepository cookRepo) {
		this.cookRepo = cookRepo;
	}
	
	@Override
	public Iterable<Cook> findAll() {
		return cookRepo.findAll();
	}
	
	@Override
	public Cook findOne( String email) {
		return cookRepo.findOne(email);
	}
	
	@Override
	public Cook save(Cook cook) {
		return cookRepo.save(cook);
	}
	
	@Override
	public void delete(String email){
		cookRepo.delete(email);
	}

}
