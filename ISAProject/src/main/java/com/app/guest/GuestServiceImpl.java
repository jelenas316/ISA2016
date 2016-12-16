package com.app.guest;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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

	@Override
	public void addFriend(String guestEmail, String friendEmail) {
		Guest guest = Optional.ofNullable(guestRepo.findOne(guestEmail))
				.orElseThrow(() -> new ResourceNotFoundException() );
		Guest friend = Optional.ofNullable(guestRepo.findOne(friendEmail))
				.orElseThrow(() -> new ResourceNotFoundException() );
		
		friend.getFriendRequests().add(guest);
		
		guestRepo.save(friend);
		
	}

	@Override
	public void deleteFriend(String guestEmail, String friendEmail) {
		Guest guest = Optional.ofNullable(guestRepo.findOne(guestEmail))
				.orElseThrow(() -> new ResourceNotFoundException() );
		Guest friend = Optional.ofNullable(guestRepo.findOne(friendEmail))
				.orElseThrow(() -> new ResourceNotFoundException() );
		
		guest.getFriends().remove(friend);
		guestRepo.save(guest);
		
	}

	@Override
	public void deleteRequest(String guestEmail, String friendEmail) {
		Guest guest = Optional.ofNullable(guestRepo.findOne(guestEmail))
				.orElseThrow(() -> new ResourceNotFoundException() );
		Guest friend = Optional.ofNullable(guestRepo.findOne(friendEmail))
				.orElseThrow(() -> new ResourceNotFoundException() );
		
		guest.getFriendRequests().remove(friend);
		
		guestRepo.save(guest);
	}

}
