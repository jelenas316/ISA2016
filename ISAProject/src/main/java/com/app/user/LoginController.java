package com.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.bidder.Bidder;
import com.app.bidder.BidderService;
import com.app.guest.Guest;
import com.app.guest.GuestService;
import com.app.restaurantmanager.RestaurantManager;
import com.app.restaurantmanager.RestaurantManagerService;
import com.app.systemmanager.SystemManager;
import com.app.systemmanager.SystemManagerService;

@RestController
@RequestMapping(path="/login")
public class LoginController {

	private final GuestService guestService;
	
	private final SystemManagerService systemManagerService;
	
	private final RestaurantManagerService restaurantManagerService;
	
	private final BidderService bidderService;
	
	@Autowired
	public LoginController(final GuestService guestService, final SystemManagerService systemManagerService,
			final RestaurantManagerService restaurantManagerService, final BidderService bidderService) {
		this.guestService=guestService;
		this.systemManagerService = systemManagerService;
		this.restaurantManagerService = restaurantManagerService;
		this.bidderService = bidderService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> login(@RequestBody LoginDTO userDTO){
		
		Object user = null;
//		Object obj= Optional.ofNullable(guestService.findOne( userDTO.getEmail()))
//				.orElseThrow(() -> new ResourceNotFoundException());
		
		if(guestService.findOne( userDTO.getEmail()) != null){
			user = guestService.findOne( userDTO.getEmail()); 
		}else if(systemManagerService.findOne(userDTO.getEmail()) != null){
			user = systemManagerService.findOne( userDTO.getEmail()); ;
		}else if(restaurantManagerService.findOne(userDTO.getEmail()) != null){
			user = restaurantManagerService.findOne( userDTO.getEmail()); ;
		}else if(bidderService.findOne(userDTO.getEmail()) != null){
			user = bidderService.findOne( userDTO.getEmail()); ;
		}else{
			throw new ResourceNotFoundException();
		}
		
		String password=getPassword(user);
		if(!password.equals(userDTO.getPassword()))
			throw new ResourceNotFoundException();
		else
			return new ResponseEntity<>(user,HttpStatus.OK);
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
		}else if(obj instanceof SystemManager){
			return ((SystemManager)obj).getPassword();
		}else if(obj instanceof RestaurantManager){
			return ((RestaurantManager)obj).getPassword();
		} if(obj instanceof Bidder){
			return ((Bidder)obj).getPassword();
		}
		
		return "";
	}
	
}
