package com.app.drink;

import java.math.BigDecimal;
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

import com.app.restaurant.Restaurant;
import com.app.restaurant.RestaurantService;

@RestController
@RequestMapping(path="/drinks")
public class DrinkController {

	private final DrinkService drinkService;
	
	private final RestaurantService restaurantService;
	
	@Autowired
	public DrinkController(final DrinkService drinkService, final RestaurantService restaurantService) {
		this.drinkService=drinkService;
		this.restaurantService = restaurantService;	
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Drink> findAll(){
		return drinkService.findAll();
	}
	
	@GetMapping(params="id")
	@ResponseStatus(HttpStatus.OK)
	public Drink findById(@PathParam("id") Long id){
		return drinkService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant save(@Valid @RequestBody DrinkDTO drink){
		int index = -1;
		BigDecimal big = new BigDecimal(drink.getPrice().toString());
		Restaurant restaurant = restaurantService.findOne(drink.getResId().longValue());
		Drink drink1= new Drink();
		drink1.setName(drink.getName());
		drink1.setDescription(drink.getDescription());
		drink1.setPrice(big);
		if(drink.getId()!= null && drinkService.findById(drink.getId()) != null){
			drink1.setId(drink.getId());
		}
		for (int i = 0; i< restaurant.getDrinks().size(); i++) {
			if(restaurant.getDrinks().get(i).getId().equals(drink.getId())){
				index = i;
			}
		}
		if(index!=-1){
			restaurant.getDrinks().remove(index);
			restaurant.getDrinks().add(drink1);
			drink1.setId(drink.getId());
		}else{
			restaurant.getDrinks().add(drink1);
		}
		drinkService.save(drink1);
		restaurantService.save(restaurant);	   
	    return restaurant;
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathParam("id") Long id){
		Optional.ofNullable(drinkService.findById(id)).orElseThrow(() -> new ResourceNotFoundException());
		drinkService.deleteById(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Drink put(@Valid @RequestBody Drink drink){
		Optional.ofNullable(drinkService.findById(drink.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return drinkService.save(drink);
	}
	
}
