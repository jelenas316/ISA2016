package com.app.restaurantmanager;

import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.app.restaurant.Restaurant;
import com.app.restaurant.RestaurantService;
import com.app.user.UserRole;

@RestController
@RequestMapping(path="/restaurantmanagers")
public class RestaurantManagerController {

	private final RestaurantManagerService restaurantManagerService;
	
	private final RestaurantService restaurantService;
	
	@Autowired
	public RestaurantManagerController(final RestaurantManagerService restaurantManagerService,
			RestaurantService restaurantService) {
		this.restaurantManagerService=restaurantManagerService;
		this.restaurantService = restaurantService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<RestaurantManager> findAll(){
		return restaurantManagerService.findAll();
	}
	
	@GetMapping(params="email")
	@ResponseStatus(HttpStatus.OK)
	public RestaurantManager findOne(@PathParam("email") String email){
		return restaurantManagerService.findOne(email);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantManager save(@Valid @RequestBody RestaurantManagerDTO restaurantManager){
		if(restaurantManagerService.findOne(restaurantManager.getEmail())!= null){
			throw new DataIntegrityViolationException("Duplicate entry for key "+ restaurantManager.getEmail());
		}
			
		Restaurant restaurant = restaurantService.findOne(restaurantManager.getRestaurant().longValue());
		RestaurantManager manager = new RestaurantManager();
		manager.setEmail(restaurantManager.getEmail());
		manager.setName(restaurantManager.getName());
		manager.setPassword(restaurantManager.getPassword());
		manager.setSurname(restaurantManager.getSurname());
		manager.setRole(UserRole.RESTAURANT_MANAGER);
		manager.setRestaurant(restaurant);
		
		return restaurantManagerService.save(manager);
	}
	
	@DeleteMapping(params="email")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathParam("email") String email){
		Optional.ofNullable(restaurantManagerService.findOne(email)).orElseThrow(() -> new ResourceNotFoundException());
		restaurantManagerService.delete(email);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public RestaurantManager put(@Valid @RequestBody RestaurantManager restaurantManager){
		Optional.ofNullable(restaurantManagerService.findOne(restaurantManager.getEmail())).orElseThrow(() -> new ResourceNotFoundException());
		return restaurantManagerService.save(restaurantManager);
	}
	
}
