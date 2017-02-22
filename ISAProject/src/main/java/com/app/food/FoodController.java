package com.app.food;

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
@RequestMapping(path="/foods")
public class FoodController {

	private final FoodService foodService;
	
	private final RestaurantService restaurantService;
	
	@Autowired
	public FoodController(final FoodService foodService, final RestaurantService restaurantService) {
		this.foodService=foodService;
		this.restaurantService = restaurantService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Food> findAll(){
		return foodService.findAll();
	}
	
	@GetMapping(params="email")
	@ResponseStatus(HttpStatus.OK)
	public Food findOne(@PathParam("id") Long id){
		return foodService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant save(@Valid @RequestBody FoodDTO food){
		int index = -1;
		BigDecimal big = new BigDecimal(food.getPrice().toString());
		Restaurant restaurant = restaurantService.findOne(food.getResId().longValue());
		Food food1 = new Food();
		food1.setName(food.getName());
		food1.setDescription(food.getDescription());
		food1.setPrice(big);
		if(food.getId()!= null && foodService.findById(food.getId()) != null){
			food1.setId(food.getId());
		}
		for (int i = 0; i< restaurant.getMenu().size(); i++) {
			if(restaurant.getMenu().get(i).getId().equals(food.getId())){
				index = i;
			}
		}
		if(index!=-1){
			restaurant.getMenu().remove(index);
			restaurant.getMenu().add(food1);
			food1.setId(food.getId());
		}else{
			restaurant.getMenu().add(food1);
		}
		foodService.save(food1);
		restaurantService.save(restaurant);	   
	    return restaurant;
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathParam("id") Long id){
		Optional.ofNullable(foodService.findById(id.longValue())).orElseThrow(() -> new ResourceNotFoundException());
		foodService.deleteById(id.longValue());
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Food put(@Valid @RequestBody Food food){
		Optional.ofNullable(foodService.findById(food.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return foodService.save(food);
	}
	
}
