package com.app.grade;

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

import com.app.guest.Guest;
import com.app.guest.GuestService;
import com.app.restaurant.Restaurant;
import com.app.restaurant.RestaurantService;

@RestController
@RequestMapping(path="/grades")
public class GradeController {

	private final GradeService gradeService;
	private final GuestService guestService;
	private final RestaurantService restaurantService;
	
	@Autowired
	public GradeController(final GradeService gradeService, final RestaurantService restaurantService,
			final GuestService guestService) {
		this.gradeService=gradeService;
		this.restaurantService=restaurantService;
		this.guestService=guestService;
	}
	

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Grade> findAll(){
		return gradeService.findAll();
	}
	
	@GetMapping(params="id")
	@ResponseStatus(HttpStatus.OK)
	public Grade findOne(@PathParam("id") Long id){
		return gradeService.findOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Grade save(@Valid @RequestBody Grade grade){
		return gradeService.save(grade);
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("id") Long id){
		Optional.ofNullable(gradeService.findOne(id)).orElseThrow(() -> new ResourceNotFoundException());
		gradeService.delete(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Grade put(@Valid @RequestBody Grade grade){
		Optional.ofNullable(gradeService.findOne(grade.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return gradeService.save(grade);
	}	
	
	@GetMapping(params="email")
	@ResponseStatus(HttpStatus.OK)
	public List<RestaurantDTO> getRestaurantsWithGrades(@PathParam("email") String email){
		Guest guest=Optional.ofNullable(guestService.findOne(email))
				.orElseThrow(() -> new ResourceNotFoundException());
		Iterable<Restaurant> allRestaurants = restaurantService.findAll();
		
		return gradeService.getRestaurantsDTO(allRestaurants,guest);
	}
	@GetMapping(path="/findGradeForWaiter", params = "email")
	@ResponseStatus(HttpStatus.OK)
	public GradeDTO findByWaiter(@PathParam("email") String email){
		return gradeService.findByWaiter(email);
	}
	@GetMapping(path="/findGradeForFood/{id}")
	@ResponseStatus(HttpStatus.OK)
	public GradeDTO findByFood(@PathVariable Long id){
		return gradeService.findByFood(id);
	}
	@GetMapping(path="/findGradeForRestaurant/{id}")
	@ResponseStatus(HttpStatus.OK)
	public GradeDTO findByRestaurant(@PathVariable Long id){
		return gradeService.findRestaurantGrade(id);
	}
}
