package com.app.guest;

import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/guests")
public class GuestController {

	private final GuestService guestService;
	
	@Autowired
	public GuestController(final GuestService guestService) {
		this.guestService=guestService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Guest> findAll(){
		return guestService.findAll();
	}
	
	@GetMapping(params="email")
	@ResponseStatus(HttpStatus.OK)
	public Guest findOne(@PathParam("email") String email){
		return guestService.findOne(email);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Guest save(@Valid @RequestBody Guest guest){
		return guestService.save(guest);
	}
	
	@DeleteMapping(params="email")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("email") String email){
		Optional.ofNullable(guestService.findOne(email)).orElseThrow(() -> new ResourceNotFoundException());
		guestService.delete(email);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Guest put(@Valid @RequestBody Guest guest){
		Optional.ofNullable(guestService.findOne(guest.getEmail())).orElseThrow(() -> new ResourceNotFoundException());
		return guestService.save(guest);
	}
	
}
