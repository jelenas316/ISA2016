package com.app.offers;

import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.bidder.Bidder;
import com.app.bidder.BidderService;
import com.app.grocerylist.GroceryList;
import com.app.grocerylist.GroceryListService;

@RestController
@RequestMapping(path="/offers")
public class OfferController {
	
	private final OfferService offerService;
	private final BidderService bidderService;
	private final GroceryListService groceryListService;
	
	@Autowired
	public OfferController(final  OfferService offerService,  final BidderService bidderService, final GroceryListService groceryListService) {
		this.offerService=offerService;
		this.bidderService = bidderService;
		this.groceryListService = groceryListService;
	}
	
	/*@RequestMapping(value = "/findAll/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Offer> findAll(@PathVariable Long id){
		Restaurant restaurant = restaurantService.findOne(id);
		return offerService.findByRestaurant(restaurant);
	}*/
	/*
	@RequestMapping(value = "/findAllForGrocery/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Offer> findAllForGrocery(@PathVariable Long id){
		GroceryList groceryList = groceryService.findOne(id);
		return offerService.findByGroceries(groceryList);
	}*/
	
	@GetMapping(params="id")
	@ResponseStatus(HttpStatus.OK)
	public Offer findOne(@PathParam("id") Long id){
		return offerService.findOne(id);
	
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Offer save(@Valid @RequestBody Offer offer){
		return offerService.save(offer);
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		Optional.ofNullable(offerService.findOne(id)).orElseThrow(() -> new ResourceNotFoundException());
		offerService.delete(id);
		
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Offer put(@Valid @RequestBody Offer offer){
		Optional.ofNullable(offerService.findOne(offer.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return offerService.save(offer);
	}
	@GetMapping(path = "/getActiveOffer", params="email")
	@ResponseStatus(HttpStatus.OK)
	public Offer getActiveOffer(@PathParam("email") String email){
		Bidder b = bidderService.findOne(email);
		return offerService.findByBidder(b);
	}
	@GetMapping(path = "/getPastOffers", params="email")
	@ResponseStatus(HttpStatus.OK)
	public List<Offer> getPastOffers(@PathParam("email") String email){
		Bidder b = bidderService.findOne(email);
		return offerService.getPastOffers(b);
	}
	@GetMapping(path = "/getOffersForGroceryList/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Offer> getOffersForGroceryList(@PathVariable Long id){
		GroceryList list = groceryListService.findOne(id);
		return offerService.findByGroceries(list);
	}
	@GetMapping(path = "/acceptOffer/{id}")
	@ResponseStatus(HttpStatus.OK)
	public boolean acceptOffer(@PathVariable Long id){
		return offerService.acceptOffer(id);
	}
	@GetMapping(path = "/rejectOffer/{id}")
	@ResponseStatus(HttpStatus.OK)
	public boolean rejectOffer(@PathVariable Long id){
		return offerService.rejectOffer(id);
	}
}
