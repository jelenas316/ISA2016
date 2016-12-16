package com.app.restaurant;

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
@RequestMapping(path="/restaurants")
public class RestaurantController {

	private final RestaurantService restaurantService;
	
	@Autowired
	public RestaurantController(final RestaurantService restaurantService) {
		this.restaurantService=restaurantService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Restaurant> findAll(){
		return restaurantService.findAll();
	}
	
	@GetMapping(params="email")
	@ResponseStatus(HttpStatus.OK)
	public Restaurant findOne(@PathParam("email") String email){
		return restaurantService.findOne(email);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant save(@Valid @RequestBody Restaurant restaurant){
		return restaurantService.save(restaurant);
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("id") String id){
		Optional.ofNullable(restaurantService.findOne(id)).orElseThrow(() -> new ResourceNotFoundException());
		restaurantService.delete(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Restaurant put(@Valid @RequestBody Restaurant restaurant){
		//Optional.ofNullable(restaurantService.findOne(restaurant.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return restaurantService.save(restaurant);
	}
	
}
