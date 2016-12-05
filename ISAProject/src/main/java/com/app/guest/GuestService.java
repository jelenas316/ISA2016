package com.app.guest;

public interface GuestService {

	Iterable<Guest> findAll();
	Guest findOne(Long id);
	Guest findByEmail(String email);
	Guest save(Guest guest);
	void delete(Guest guest);
	
}
