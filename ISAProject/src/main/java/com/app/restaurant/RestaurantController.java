package com.app.restaurant;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

import com.app.drink.Drink;
import com.app.food.Food;
import com.app.restauranttable.RestaurantTable;

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
	
	@GetMapping(params="id")
	@ResponseStatus(HttpStatus.OK)
	public Restaurant findOne(@PathParam("id") Long id){
		return restaurantService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant save(@Valid @RequestBody Restaurant restaurant){
		Set<Drink> drinkCard = new HashSet<Drink>();
		Set<Food> menu = new HashSet<Food>();
		Set<RestaurantTable> tables= new HashSet<RestaurantTable>();
		restaurant.setDrinkCard(drinkCard);
		restaurant.setMenu(menu);
		restaurant.setTables(tables);
		return restaurantService.save(restaurant);
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathParam("id") Long id){
		Optional.ofNullable(restaurantService.findById(id)).orElseThrow(() -> new ResourceNotFoundException());
		restaurantService.deleteById(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Restaurant put(@Valid @RequestBody Restaurant restaurant){
		Optional.ofNullable(restaurantService.findById(restaurant.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return restaurantService.save(restaurant);
	}
	
}
