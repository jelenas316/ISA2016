package com.app.guest;

public interface GuestService {

	Iterable<Guest> findAll();
	Guest findOne(String email);
	Guest save(Guest guest);
	void delete(String email);
	
}
