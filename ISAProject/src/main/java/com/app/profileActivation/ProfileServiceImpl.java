package com.app.profileActivation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService{

	private final ProfileRepository profileRepo;
	
	@Autowired
	public ProfileServiceImpl(final ProfileRepository profileRepo) {
		this.profileRepo = profileRepo;
	}
	
	@Override
	public Iterable<Profile> findAll() {
		return profileRepo.findAll();
	}

	@Override
	public Profile findOne(Long id) {
		return profileRepo.findOne(id);
	}

	@Override
	public Profile save(Profile profile) {
		return profileRepo.save(profile);
	}

	@Override
	public void delete(Long id) {
		profileRepo.delete(id);
	}

	@Override
	public Iterable<Profile> findByEmail(String email) {
		return profileRepo.findByEmail(email);
	}

}
