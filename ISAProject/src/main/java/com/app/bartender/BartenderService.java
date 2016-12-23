package com.app.bartender;

public interface BartenderService {
	
	Iterable<Bartender> findAll();
	Bartender findOne(String email);
	Bartender save(Bartender bartender);
	void delete(String email);
	

}
