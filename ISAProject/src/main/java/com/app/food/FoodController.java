package com.app.food;

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
@RequestMapping(path="/foods")
public class FoodController {

	private final FoodService foodService;
	
	@Autowired
	public FoodController(final FoodService foodService) {
		this.foodService=foodService;
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
	public Food save(@Valid @RequestBody Food food){
		return foodService.save(food);
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathParam("id") Long id){
		Optional.ofNullable(foodService.findById(id)).orElseThrow(() -> new ResourceNotFoundException());
		foodService.deleteById(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Food put(@Valid @RequestBody Food food){
		Optional.ofNullable(foodService.findById(food.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return foodService.save(food);
	}
	
}
