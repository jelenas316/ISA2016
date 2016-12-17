package com.app.grade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
		Iterable<Grade> grades = gradeService.findAll();
		Iterable<Restaurant> allRestaurants = restaurantService.findAll();
		List<RestaurantDTO> restaurantsDTO=new ArrayList<>();
		RestaurantDTO dto=new RestaurantDTO();
		for (Restaurant r : allRestaurants) {
			dto=restaurantToDTO(r);
			List<Grade> gradesForRestaurant=getGradesForRestaurant(grades, r.getId());
			dto.setReiting(calculateGradeForAllUsers(gradesForRestaurant));
			dto.setFriendsReiting(calculateGradeForFriends(guest,gradesForRestaurant));
			restaurantsDTO.add(dto);
		}
		return restaurantsDTO;
	}
	
	/**
	 * Returns all grades for passed restaurant.
	 */
	private List<Grade> getGradesForRestaurant(Iterable<Grade> grades, Long restaurantId){
		List<Grade> returnValue=new ArrayList<>();
		
		for(Grade grade : grades){
			if(grade.getRestaurant()!=null && grade.getRestaurant().getId().equals(restaurantId))
				returnValue.add(grade);
		}
		
		return returnValue;
	}
	
	/**
	 * Method that converts {@link Restaurant} to {@link RestaurantDTO}.
	 */
	private RestaurantDTO restaurantToDTO(Restaurant restaurant){
		RestaurantDTO dto=new RestaurantDTO();
		dto.setDescription(restaurant.getDescription());
		dto.setId(restaurant.getId());
		dto.setName(restaurant.getName());
		dto.setFriendsReiting(0);
		dto.setReiting(0);
		return dto;
	}
	
	/**
	 * Calculates grade for restaurant for all users.
	 * 
	 * @param grades
	 * @return reiting for all users
	 */
	private Integer calculateGradeForAllUsers(List<Grade> grades){
		Integer value = 0;
		if(grades.size()>0){
			for(Grade grade : grades){
				value+=grade.getValue();
			}
			BigDecimal average=new BigDecimal(value/grades.size());
			value=average.intValue();
		}
		return value;
	}
	
	/**
	 * Calculates grade for restaurant for friends of the guest and guest.
	 * 
	 * @param email - email of the guest
	 * @return 
	 */
	private Integer calculateGradeForFriends(Guest guest, List<Grade> grades){
		Integer value = 0;
		Integer counter=0;
		if(grades.size()>0){
			for(Grade grade : grades){
				if(grade.getGuest().getEmail().equals(guest.getEmail()) ||
						guest.getFriends().contains(grade.getGuest())){
					value+=grade.getValue();
					counter++;
				}
			}
			if(counter>0){
				BigDecimal average=new BigDecimal(value/counter);
				value=average.intValue();
			}
		}
		return value;
	}
	
	
}
