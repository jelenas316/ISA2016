package com.app.user;

public interface UserService {

	Iterable<User> findAll();
	User findOne(String email);
	User save(User user);
	void delete(String email);
	
}
