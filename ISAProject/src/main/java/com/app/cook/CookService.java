package com.app.cook;

public interface CookService {
	
	Iterable<Cook> findAll();
	Cook findOne(String email);
	Cook save(Cook cook);
	void delete(String email);

}
