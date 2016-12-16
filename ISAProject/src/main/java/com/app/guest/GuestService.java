package com.app.guest;

public interface GuestService {

	Iterable<Guest> findAll();
	Guest findOne(String email);
	Guest save(Guest guest);
	void delete(String email);
	
	void addFriend(String guestEmail, String friendEmail);
	void deleteFriend(String guestEmail, String friendEmail);
	
	void deleteRequest(String guestEmail, String friendEmail);
	
}
