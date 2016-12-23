package com.app.restauranttable;

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
@RequestMapping(path="/restauranttables")
public class RestaurantTableController {

	private final RestaurantTableService restaurantTableService;
	
	@Autowired
	public RestaurantTableController(final RestaurantTableService restaurantTableService) {
		this.restaurantTableService=restaurantTableService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<RestaurantTable> findAll(){
		return restaurantTableService.findAll();
	}
	
	@GetMapping(params="email")
	@ResponseStatus(HttpStatus.OK)
	public RestaurantTable findOne(@PathParam("id") Long id){
		return restaurantTableService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantTable save(@Valid @RequestBody RestaurantTable restaurantTable){
		return restaurantTableService.save(restaurantTable);
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathParam("id") Long id){
		Optional.ofNullable(restaurantTableService.findById(id)).orElseThrow(() -> new ResourceNotFoundException());
		restaurantTableService.deleteById(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public RestaurantTable put(@Valid @RequestBody RestaurantTable restaurantTable){
		Optional.ofNullable(restaurantTableService.findById(restaurantTable.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return restaurantTableService.save(restaurantTable);
	}
	
}
