package com.app.bidder;

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

import com.app.offers.Offer;
import com.app.restaurant.Restaurant;
import com.app.restaurant.RestaurantService;

@RestController
@RequestMapping(path="/bidders")
public class BidderController {
	
	private final BidderService bidderService;
	private final RestaurantService restaurantService;
	
	@Autowired
	public BidderController(final BidderService bidderService, final RestaurantService restaurantService) {
		this.bidderService=bidderService;
		this.restaurantService = restaurantService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Bidder> findAll(){
		return bidderService.findAll();
	}
	@RequestMapping(value = "/findAll/{restaurant}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Bidder> findAll(@PathVariable Long restaurant){
		Restaurant res = restaurantService.findOne(restaurant);
		return bidderService.findByRestaurant(res);
	}
	
	@GetMapping(params="email")
	@ResponseStatus(HttpStatus.OK)
	public Bidder findOne(@PathParam("email") String email){
		return bidderService.findOne(email);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Bidder save(@Valid @RequestBody Bidder bidder){
		if(bidderService.findOne(bidder.getEmail()) == null){
			return bidderService.save(bidder);
		}
		return null;
	}
	
	@DeleteMapping(params="email")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("email") String email){
		Optional.ofNullable(bidderService.findOne(email)).orElseThrow(() -> new ResourceNotFoundException());
		bidderService.delete(email);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Bidder put(@Valid @RequestBody Bidder bidder){
		Optional.ofNullable(bidderService.findOne(bidder.getEmail())).orElseThrow(() -> new ResourceNotFoundException());
		return bidderService.save(bidder);
	}
	
}
