package com.app.guest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/guests")
public class GuestController {

	private final GuestService guestService;
	
	public GuestController(final GuestService guestService) {
		this.guestService=guestService;
	}
	
	@GetMapping
	public Iterable<Guest> findAll(){
		return guestService.findAll();
	}

}
