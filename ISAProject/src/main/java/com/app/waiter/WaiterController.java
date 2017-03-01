package com.app.waiter;

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

import com.app.order.Order;
import com.app.order.OrderService;
import com.app.order.OrderedDrinkService;
import com.app.order.OrderedFoodService;
import com.app.restaurant.Restaurant;
import com.app.restaurant.RestaurantService;
import com.app.shift.ShiftDTO;

@RestController
@RequestMapping(path = "/waiters")
public class WaiterController {

	private final WaiterService waiterService;
	private final RestaurantService restaurantService;
	private final OrderService orderService;
	private final OrderedFoodService orderedFoodService;
	private final OrderedDrinkService orderedDrinkService;
	
	@Autowired
	public WaiterController(final WaiterService waiterService, final RestaurantService restaurantService, final OrderService orderService,
							final OrderedFoodService orderedFoodService, final OrderedDrinkService orderedDrinkService){
		this.waiterService = waiterService;
		this.restaurantService = restaurantService;
		this.orderService = orderService;
		this.orderedFoodService = orderedFoodService;
		this.orderedDrinkService = orderedDrinkService;
	}
	
	@RequestMapping(value = "/findAll/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Waiter> findAll(@PathVariable Long id){
		Restaurant restaurant = restaurantService.findOne(id);
		return waiterService.findByRestaurant(restaurant);
	}
	
	@GetMapping(params = "email")
	@ResponseStatus(HttpStatus.OK)
	public Waiter findOne(@PathParam("email") String email){
		return waiterService.findOne(email);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Waiter save(@Valid @RequestBody Waiter waiter){
		if(waiterService.findOne(waiter.getEmail()) == null){
			return waiterService.save(waiter);			
		}
		return null;
	}
	
	@DeleteMapping(params = "email")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("email") String email){
		Optional.ofNullable(waiterService.findOne(email)).orElseThrow(() -> new ResourceNotFoundException());
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Waiter put(@Valid @RequestBody Waiter waiter){
		Optional.ofNullable(waiterService.findOne(waiter.getEmail())).orElseThrow(() -> new ResourceNotFoundException());
		return waiterService.save(waiter);
	}
	@RequestMapping(value = "/deleteShift", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteShift(@RequestBody Map<String,String> requestParams){
		Long shift=Long.parseLong(requestParams.get("shift"));
		String email=requestParams.get("email");
		waiterService.deleteShift(email, shift);
	}
	@RequestMapping(value = "/addShift", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public boolean addShift(@RequestBody ShiftDTO shift){
		return waiterService.addShift(shift);		 
	}
	
	@PutMapping(params={"order", "orderedFood"})
	@ResponseStatus(HttpStatus.OK)
	public void cancelOrderedFood(@PathParam("order") Long order, 
			@PathParam("orderedFood") Long orderedFood){
		Order orderObject = Optional.ofNullable(orderService.findOne(order))
				.orElseThrow(() -> new ResourceNotFoundException());
		Optional.ofNullable(orderedFoodService.findOne(orderedFood))
			.orElseThrow(() -> new ResourceNotFoundException());
		waiterService.cancelFood(orderObject, orderedFood);
	}
	
	@PutMapping(params={"order", "orderedDrink"})
	@ResponseStatus(HttpStatus.OK)
	public void cancelOrderedDrink(@PathParam("order") Long order, 
			@PathParam("orderedDrink") Long orderedDrink){
		Order orderObject = Optional.ofNullable(orderService.findOne(order))
				.orElseThrow(() -> new ResourceNotFoundException());
		Optional.ofNullable(orderedDrinkService.findOne(orderedDrink))
			.orElseThrow(() -> new ResourceNotFoundException());
		waiterService.cancelDrink(orderObject, orderedDrink);
	}
}
