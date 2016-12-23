package com.app.drink;

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
@RequestMapping(path="/drinks")
public class DrinkController {

	private final DrinkService drinkService;
	
	@Autowired
	public DrinkController(final DrinkService drinkService) {
		this.drinkService=drinkService;
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
	public Drink save(@Valid @RequestBody Drink drink){
		return drinkService.save(drink);
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
