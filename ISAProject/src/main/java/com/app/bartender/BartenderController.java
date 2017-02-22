package com.app.bartender;

import java.util.Map;
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

import com.app.restaurant.Restaurant;
import com.app.restaurant.RestaurantService;
import com.app.shift.ShiftDTO;

@RestController
@RequestMapping(path = "/bartenders")
public class BartenderController {
	
	private final BartenderService bartenderService;
	private final RestaurantService restaurantService;
	
	@Autowired
	public BartenderController(final BartenderService bartenderService, RestaurantService restaurantService){
		this.bartenderService = bartenderService;
		this.restaurantService = restaurantService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Bartender> findAll(){
		return bartenderService.findAll();
	}
	@RequestMapping(value = "/findAll/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Bartender> findAll(@PathVariable Long id){
		Restaurant restaurant = restaurantService.findOne(id);
		return bartenderService.findByRestaurant(restaurant);
	}
	
	@GetMapping(params = "email")
	@ResponseStatus(HttpStatus.OK)
	public Bartender findOne(@PathParam("email") String email){
		return bartenderService.findOne(email);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Bartender save(@Valid @RequestBody Bartender bartender){
		if(bartenderService.findOne(bartender.getEmail()) == null){
			return bartenderService.save(bartender);			
		}
		return null;
	}
	
	@DeleteMapping(params = "email")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("email") String email){
		Optional.ofNullable(bartenderService.findOne(email)).orElseThrow(() -> new ResourceNotFoundException());
		bartenderService.delete(email);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Bartender put(@Valid @RequestBody Bartender bartender) {
		Optional.ofNullable(bartenderService.findOne(bartender.getEmail())).orElseThrow(() -> new ResourceNotFoundException());
		return bartenderService.save(bartender);
	}
	
	@RequestMapping(value = "/deleteShift", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteShift(@RequestBody Map<String,String> requestParams){
		Long shift=Long.parseLong(requestParams.get("shift"));
		String email=requestParams.get("email");
		bartenderService.deleteShift(email, shift);
	}
	@RequestMapping(value = "/addShift", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public boolean addShift(@RequestBody ShiftDTO shift){
		return bartenderService.addShift(shift);		 
	}

}
