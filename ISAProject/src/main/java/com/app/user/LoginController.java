package com.app.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.guest.Guest;
import com.app.guest.GuestService;

@RestController
@RequestMapping(path="/login")
public class LoginController {

	private final GuestService guestService;
	
	@Autowired
	public LoginController(final GuestService guestService) {
		this.guestService=guestService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public Object login(@RequestBody LoginDTO userDTO){
		Object obj= Optional.ofNullable( guestService.findOne( userDTO.getEmail() ) )
				.orElseThrow(() -> new ResourceNotFoundException());
		String password=getPassword(obj);
		if(!password.equals(userDTO.getPassword()))
			throw new ResourceNotFoundException();
		else
			return obj;
	}
	
	/**
	 * Method that returns password for forwarded object.
	 * 
	 * @param obj - Object that can be any user.
	 * @return password of the user
	 */
	private String getPassword(Object obj){
		if(obj instanceof Guest){
			return ((Guest)obj).getPassword();
		}
		
		return "";
	}
	
}
