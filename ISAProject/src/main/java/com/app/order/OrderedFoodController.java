package com.app.order;

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
@RequestMapping(path="/orderedFood")
public class OrderedFoodController {


	private final OrderedFoodService orderedFoodService;
	
	@Autowired
	public OrderedFoodController(final OrderedFoodService orderedFoodService) {
		this.orderedFoodService = orderedFoodService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<OrderedFood> findAll(){
		return orderedFoodService.findAll();
	}
	
	@GetMapping(params="id")
	@ResponseStatus(HttpStatus.OK)
	public OrderedFood findOne(@PathParam("id") Long id){
		return orderedFoodService.findOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderedFood save(@Valid @RequestBody OrderedFood orderedFood){
		return orderedFoodService.save(orderedFood);
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("id") Long id){
		Optional.ofNullable(orderedFoodService.findOne(id)).orElseThrow(() -> new ResourceNotFoundException());
		orderedFoodService.delete(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public OrderedFood put(@Valid @RequestBody OrderedFood orderedFood){
		Optional.ofNullable(orderedFoodService.findOne(orderedFood.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return orderedFoodService.save(orderedFood);
	}
	
}
