package com.app.reservation;

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

import com.app.grade.GradeService;
import com.app.grade.RestaurantDTO;
import com.app.guest.Guest;
import com.app.guest.GuestService;
import com.app.restaurant.Restaurant;

@RestController
@RequestMapping(path="/reservations")
public class ReservationController {
	
	private final ReservationService reservationService;
	private final GradeService gradeService;
	private final GuestService guestService;
	
	@Autowired
	public ReservationController(final ReservationService reservationService,
			final GradeService gradeService, final GuestService guestService) {
		this.reservationService = reservationService;
		this.gradeService = gradeService;
		this.guestService = guestService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Reservation> findAll(){
		return reservationService.findAll();
	}
	
	@GetMapping(params="id")
	@ResponseStatus(HttpStatus.OK)
	public Reservation findOne(@PathParam("id") Long id){
		return reservationService.findOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Reservation save(@Valid @RequestBody Reservation reservation){
		return reservationService.save(reservation);
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("id") Long id){
		Optional.ofNullable(reservationService.findOne(id)).orElseThrow(() -> new ResourceNotFoundException());
		reservationService.delete(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Reservation put(@Valid @RequestBody Reservation reservation){
		Optional.ofNullable(reservationService.findOne(reservation.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return reservationService.save(reservation);
	}
	
	@GetMapping(params="email")
	@ResponseStatus(HttpStatus.OK)
	public List<VisitedRestaurantDTO> findVisitedRestaurants(@PathParam("email") String email){
		Guest guest=Optional.ofNullable(guestService.findOne(email))
				.orElseThrow(() -> new ResourceNotFoundException());
		Iterable<Reservation> allReservations=reservationService.findPreviousByGuest(email);
		List<RestaurantDTO> restaurantDTOs=getRestaurantDTOs(guest, allReservations);
		
		return reservationToDTO(allReservations,restaurantDTOs,guest);
	}
	
	public List<VisitedRestaurantDTO> reservationToDTO(Iterable<Reservation> reservations, 
			List<RestaurantDTO> restaurantDTOs, Guest guest){
		List<VisitedRestaurantDTO> returnValue = new ArrayList<>();
		VisitedRestaurantDTO dto = new VisitedRestaurantDTO();
		for(Reservation reservation : reservations){
			dto = new VisitedRestaurantDTO();
			dto.setArrival(reservation.getArrival());
			dto.setFriends(getFriends(reservation.getGuests(),guest));
			dto.setId(reservation.getId());
			dto.setRestaurant(findRestaurant(restaurantDTOs, reservation.getRestaurant().getId()));
			returnValue.add(dto);
		}
		
		return returnValue;
	}
	
	
	public RestaurantDTO findRestaurant(List<RestaurantDTO> restaurantDTOs, Long restaurantId){
		for(RestaurantDTO restaurant : restaurantDTOs)
			if(restaurant.getId().equals(restaurantId))
				return restaurant;
		return null;
	}
	
	
	/**
	 * Returns friends without guest.
	 */
	public List<Guest> getFriends(List<Guest> guests,Guest guest){
		guests.remove(guest);
		return guests;
	}
	
	/**
	 * Returns list of {@link RestaurantDTO} with all restaurants in reservations.
	 */
	public List<RestaurantDTO> getRestaurantDTOs(Guest guest, Iterable<Reservation> allReservations){
		List<Restaurant> restaurants=new ArrayList<>();
		for(Reservation reservation : allReservations)
			restaurants.add(reservation.getRestaurant());
		return gradeService.getRestaurantsDTO(restaurants, guest);
	}


}



