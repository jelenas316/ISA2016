package com.app.restauranttable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.restaurant.Restaurant;
import com.app.restaurant.RestaurantService;

@RestController
@RequestMapping(path="/restauranttables")
public class RestaurantTableController {

	private final RestaurantTableService restaurantTableService;
	
	private final RestaurantService restaurantService;
	
	@Autowired
	public RestaurantTableController(final RestaurantTableService restaurantTableService, final RestaurantService restaurantService) {
		this.restaurantTableService=restaurantTableService;
		this.restaurantService = restaurantService;
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
	public Restaurant save(@Valid @RequestBody RestaurantTableDTO configuration){
		
		Restaurant restaurant = restaurantService.findOne(configuration.getRestaurant());
			
		return setTablesForRestaurant(restaurant, configuration.getTables());
	}
		
	@RequestMapping(value="/{tables}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long[] tables){
		
		deleteTablesFromRestaurant(tables);
	
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public RestaurantTable put(@Valid @RequestBody RestaurantTable restaurantTable){
		Optional.ofNullable(restaurantTableService.findById(restaurantTable.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return restaurantTableService.save(restaurantTable);
	}
	
	private Restaurant setTablesForRestaurant(Restaurant restaurant, List<RestaurantTable> newTables){
		
		List<RestaurantTable> tables = restaurant.getTables();
		int num = tables.size();
		
		for (RestaurantTable table : newTables) {
			num = num+1;
			table.setNumber(num);
			table.setStatus(Status.FREE);
			restaurantTableService.save(table);
			tables.add(table);
		}
		restaurant.setTables(tables);
		restaurantService.save(restaurant);
		
		return restaurant;
	}
private void deleteTablesFromRestaurant(Long[] tables){
		
	List<Long> tablesIds = new ArrayList<Long>();
	int num = 0;
	
	for (Long tableId : tables) {
		tablesIds.add(tableId);
	}
	int index = tablesIds.size()-1;
	Long restaurantId = tablesIds.get(index);
	tablesIds.remove(index);
	
	for (Long tableId : tablesIds) {
		try {
			restaurantTableService.deleteById(tableId);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Cannot delete table with id \""+tableId+"\" because it have schedule.");
		}
	}
	
	Restaurant restaurant = restaurantService.findOne(restaurantId);
	for (RestaurantTable table : restaurant.getTables()) {
		num = num+1;
		table.setNumber(num);
	}
	restaurantService.save(restaurant);
	}
	
}
