package com.app.cook;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.restaurant.Restaurant;
import com.app.restaurant.RestaurantService;
import com.app.shift.ShiftDTO;

@RestController
@RequestMapping(path = "/cooks")
public class CookController {
	
	private final CookService cookService;
	private final RestaurantService restaurantService;
	
	@Autowired
	public CookController(final CookService cookService, final RestaurantService restaurantService) {
		this.cookService = cookService;
		this.restaurantService=restaurantService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Cook> findAll() {
		return cookService.findAll();
	}
	
	@RequestMapping(value = "/findAll/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Cook> findAll(@PathVariable Long id){
		Restaurant restaurant = restaurantService.findOne(id);
		return cookService.findByRestaurant(restaurant);
	}
	
	@GetMapping(params = "email")
	@ResponseStatus(HttpStatus.OK)
	public Cook findOne(@PathParam("email") String email){
		return cookService.findOne(email);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cook save(@Valid @RequestBody Cook cook){
		if(cookService.findOne(cook.getEmail()) == null){
			return cookService.save(cook);			
		}
		return null;
	}
	
	@DeleteMapping(params = "email")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("email") Cook cook){
		Optional.ofNullable(cookService.findOne(cook.getEmail())).orElseThrow(() -> new ResourceNotFoundException());
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Cook put(@Valid @RequestBody Cook cook){
		Optional.ofNullable(cookService.findOne(cook.getEmail())).orElseThrow(() -> new ResourceNotFoundException());
		return cookService.save(cook);
	}
	@RequestMapping(value = "/deleteShift", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteShift(@RequestBody Map<String,String> requestParams){
		Long shift=Long.parseLong(requestParams.get("shift"));
		String email=requestParams.get("email");
		cookService.deleteShift(email, shift);
	}
	@RequestMapping(value = "/addShift", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public boolean addShift(@RequestBody ShiftDTO shift){
		return cookService.addShift(shift);		 
	}
}
