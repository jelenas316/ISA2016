package com.app.users;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	private final UserRepo userRepo;
	
	@Autowired
	public UserServiceImpl(final UserRepo userRepo){
		this.userRepo = userRepo;
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
		return  userRepo.save(user);
	}

	@Override
	public void delete(String email) {
		userRepo.delete(email);
	}

}
