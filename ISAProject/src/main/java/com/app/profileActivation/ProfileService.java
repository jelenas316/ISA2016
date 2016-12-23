package com.app.profileActivation;

public interface ProfileService {

	Iterable<Profile> findAll();
	Profile findOne(Long id);
	Profile save(Profile profile);
	void delete(Long id);
	
	Iterable<Profile> findByEmail(String email);
	
}
