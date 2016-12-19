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
@RequestMapping("/orderedDrinks")
public class OrderedDrinkController {


	private final OrderedDrinkService orderedDrinkService;
	
	@Autowired
	public OrderedDrinkController(final OrderedDrinkService orderedDrinkService) {
		this.orderedDrinkService = orderedDrinkService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<OrderedDrink> findAll(){
		return orderedDrinkService.findAll();
	}
	
	@GetMapping(params="id")
	@ResponseStatus(HttpStatus.OK)
	public OrderedDrink findOne(@PathParam("id") Long id){
		return orderedDrinkService.findOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderedDrink save(@Valid @RequestBody OrderedDrink orderedDrink){
		return orderedDrinkService.save(orderedDrink);
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("id") Long id){
		Optional.ofNullable(orderedDrinkService.findOne(id)).orElseThrow(() -> new ResourceNotFoundException());
		orderedDrinkService.delete(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public OrderedDrink put(@Valid @RequestBody OrderedDrink orderedDrink){
		Optional.ofNullable(orderedDrinkService.findOne(orderedDrink.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return orderedDrinkService.save(orderedDrink);
	}
	
}
