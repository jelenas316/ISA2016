package com.app.restaurant;

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

import com.app.drinkgrocery.DrinkGrocery;
import com.app.foodStuff.FoodStuff;

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
		
	 Iterable<Restaurant> res = restaurantService.findAll();
		return res;
	}
	@GetMapping(path = "/findRestaurantsWithoutManagers")
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Restaurant> findRestaurantsWithoutManagers(){		
		return restaurantService.findRestaurantsWithoutManagers();
	}
	
	@GetMapping(params="id")
	@ResponseStatus(HttpStatus.OK)
	public Restaurant findOne(@PathParam("id") Long id){
		return restaurantService.findOne(id);
	
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant save(@Valid @RequestBody Restaurant restaurant){
		/*Set<Drink> drinkCard = new HashSet<Drink>();
		Set<Food> menu = new HashSet<Food>();
		Set<RestaurantTable> tables= new HashSet<RestaurantTable>();
		restaurant.setDrinkCard(drinkCard);
		restaurant.setMenu(menu);
		restaurant.setTables(tables);*/
		return restaurantService.save(restaurant);
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Optional.ofNullable(restaurantService.findOne(id)).orElseThrow(() -> new ResourceNotFoundException());
		restaurantService.delete(id);
		
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Restaurant put(@Valid @RequestBody Restaurant restaurant){
		Optional.ofNullable(restaurantService.findOne(restaurant.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return restaurantService.save(restaurant);
	}
	@RequestMapping(value = "/getFoodStuff/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Iterable<FoodStuff> getFoodStuff(@PathVariable Long id){
		Restaurant res = restaurantService.findOne(id);
		return restaurantService.getFoodStuff(res);	
	}
	@RequestMapping(value = "/getDrinkGroceries/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Iterable<DrinkGrocery> getDrinkGroceries(@PathVariable Long id){
		Restaurant res = restaurantService.findOne(id);
		return restaurantService.getDrinkGroceries(res);	
	}
	
}
