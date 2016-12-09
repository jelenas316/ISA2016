package com.app.guest;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GuestServiceImpl implements GuestService{

	private final GuestRepository guestRepo;
	
	@Autowired
	public GuestServiceImpl(final GuestRepository guestRepo) {
		this.guestRepo=guestRepo;
	}
	
	@Override
	public Iterable<Guest> findAll() {
		return guestRepo.findAll();
	}

	@Override
	public Guest findOne(String email) {
		return guestRepo.findOne(email);
	}

	@Override
	public Guest save(Guest guest) {
		return guestRepo.save(guest);
	}

	@Override
	public void delete(String email) {
		guestRepo.delete(email);
	}

}
