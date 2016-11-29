package com.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	private final UserRepository userRepo;
	
	@Autowired
	public UserServiceImpl(final UserRepository userRepo) {
		this.userRepo=userRepo;
	}
	
	@Override
	public Iterable<User> findAll() {
		return userRepo.findAll();
	}

	@Override
	public User findOne(String email) {
		return userRepo.findOne(email);
	}

	@Override
	public User save(User user) {
		return userRepo.save(user);
	}

	@Override
	public void delete(String email) {
		userRepo.delete(email);
	}

}
