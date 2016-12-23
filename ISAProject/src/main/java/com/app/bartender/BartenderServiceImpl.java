package com.app.bartender;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BartenderServiceImpl implements BartenderService{
	
	private final BartenderRepository bartenderRepo;
	
	@Autowired
	public BartenderServiceImpl(final BartenderRepository baretnderRepo){
		this.bartenderRepo = baretnderRepo;
		
	}
	
	@Override
	public Iterable<Bartender> findAll() {
		return bartenderRepo.findAll();
	}
	
	@Override
	public Bartender findOne(String email){
		return bartenderRepo.findOne(email);
	}
	
	@Override
	public Bartender save(Bartender bartender){
		return bartenderRepo.save(bartender);
	}
	
	@Override
	public void delete(String email){
		bartenderRepo.delete(email);
	}

}
