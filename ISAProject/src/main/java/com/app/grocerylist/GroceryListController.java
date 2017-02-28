package com.app.grocerylist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.app.offers.Offer;
import com.app.offers.OfferService;
import com.app.restaurant.Restaurant;
import com.app.restaurant.RestaurantService;

@RestController
@RequestMapping(path = "/groceries")
public class GroceryListController {
	
	private final GroceryListService groceryListService;
	private final RestaurantService restaurantService;
	private final OfferService offerService;
	
	@Autowired
	public GroceryListController(final GroceryListService groceryListService, final RestaurantService restaurantService,
			 final OfferService offerService){
		this.groceryListService = groceryListService;
		this.restaurantService = restaurantService;
		this.offerService = offerService;
	}
	
	@RequestMapping(value = "/findAll/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public GroceryList findAll(@PathVariable Long id){
		Restaurant restaurant = restaurantService.findOne(id);
		return groceryListService.findByRestaurantActive(restaurant);
	}
	@RequestMapping(value = "/findPastOffers/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Offer> findPastOffers(@PathVariable Long id){
		Restaurant restaurant = restaurantService.findOne(id);
		List<GroceryList> list =  groceryListService.findByRestaurant(restaurant);
		List<GroceryList> pastList = new ArrayList<GroceryList>();
		List<Offer> pastOffers = new ArrayList<Offer>();
		for (GroceryList groceryList : list) {
			if(groceryList.isAccepted()){
				pastList.add(groceryList);
			}
		}
		for (GroceryList groceryList : pastList) {
			Offer o =  offerService.getPastForGroceryList(groceryList);
			pastOffers.add(o);
		}
		return pastOffers;
	}
	
	@GetMapping(path="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public GroceryList findOne(@PathVariable Long id){
		return groceryListService.findOne(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GroceryList save(@Valid @RequestBody GroceryList groceryList){
		return groceryListService.save(groceryList);		
	}	
	
	@DeleteMapping(path="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id){
		 groceryListService.delete(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public GroceryList put(@Valid @RequestBody GroceryList groceryList){
		Optional.ofNullable(groceryListService.findOne(groceryList.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return groceryListService.save(groceryList);
	}

}
