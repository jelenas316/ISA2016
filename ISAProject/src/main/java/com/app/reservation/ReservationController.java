package com.app.reservation;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.MailService;
import com.app.grade.GradeService;
import com.app.grade.RestaurantDTO;
import com.app.guest.Guest;
import com.app.guest.GuestService;
import com.app.order.Order;
import com.app.order.OrderService;
import com.app.order.OrderedDrinkService;
import com.app.order.OrderedFoodService;
import com.app.restaurant.Restaurant;
import com.app.restauranttable.RestaurantTable;

@RestController
@RequestMapping(path="/reservations")
public class ReservationController {
	
	private final ReservationService reservationService;
	private final GradeService gradeService;
	private final GuestService guestService;
	private final MailService mailService;
	private final OrderService orderService;
	private final OrderedFoodService orderedFoodService;
	private final OrderedDrinkService orderedDrinkService;
	
	@Autowired
	public ReservationController(final ReservationService reservationService,
			final GradeService gradeService, final GuestService guestService,
			final MailService mailService, final OrderService orderService,
			final OrderedFoodService orderedFoodService, final OrderedDrinkService orderedDrinkService) {
		this.reservationService = reservationService;
		this.gradeService = gradeService;
		this.guestService = guestService;
		this.mailService = mailService;
		this.orderService = orderService;
		this.orderedFoodService = orderedFoodService;
		this.orderedDrinkService = orderedDrinkService;
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
		Reservation saved = reservationService.save(reservation);
		
		for(Guest guest : saved.getInvitedFriends()){
			String message = "You have been invited to an event. See in the link: " 
					+ "http://localhost:8080/#/invitation/"+saved.getId()+
					"?email="+ guest.getEmail();
			try{
				mailService.sendMail(guest.getEmail(), message, "Invitation");
			}catch(MailException e){
				e.printStackTrace();
			}
		}
		
		return saved;
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("id") Long id){
		Optional.ofNullable(reservationService.findOne(id)).orElseThrow(() -> new ResourceNotFoundException());
		reservationService.delete(id);
	}
	
	@PostMapping(params={"id","email"})
	public ResponseEntity<Object> acceptInvitation(@PathParam("id") Long id, @PathParam("email") String email){
		Reservation reservation=Optional.ofNullable(reservationService.findOne(id)).
				orElseThrow(() -> new ResourceNotFoundException());
		for(Guest guest:reservation.getInvitedFriends()){
			if(guest.getEmail().equals(email)){
				reservation.getInvitedFriends().remove(guest);
				reservation.getGuests().add(guest);
				reservationService.save(reservation);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}
		throw new ResourceNotFoundException();
	}
	
	@DeleteMapping(params={"id","email"})
	public ResponseEntity<Object> rejectInvitation(@PathParam("id") Long id, @PathParam("email") String email){
		Reservation reservation=Optional.ofNullable(reservationService.findOne(id)).
				orElseThrow(() -> new ResourceNotFoundException());
		for(Guest guest:reservation.getInvitedFriends()){
			if(guest.getEmail().equals(email)){
				reservation.getInvitedFriends().remove(guest);
				reservationService.save(reservation);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		}
		throw new ResourceNotFoundException();
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
	
	@GetMapping(path="/next", params="email")
	@ResponseStatus(HttpStatus.OK)
	public List<Reservation> findFutureReservations(@PathParam("email") String email){
		return reservationService.findFutureReservationsForGuest(email);
	}
	
	@PutMapping(params={"order", "orderedFood"})
	@ResponseStatus(HttpStatus.OK)
	public void cancelOrderedFood(@PathParam("order") Long order, 
			@PathParam("orderedFood") Long orderedFood){
		Order orderObject = Optional.ofNullable(orderService.findOne(order))
				.orElseThrow(() -> new ResourceNotFoundException());
		Optional.ofNullable(orderedFoodService.findOne(orderedFood))
			.orElseThrow(() -> new ResourceNotFoundException());
		reservationService.cancelOrderedFood(orderObject, orderedFood);
	}
	
	@PutMapping(params={"order", "orderedDrink"})
	@ResponseStatus(HttpStatus.OK)
	public void cancelOrderedDrink(@PathParam("order") Long order, 
			@PathParam("orderedDrink") Long orderedDrink){
		Order orderObject = Optional.ofNullable(orderService.findOne(order))
				.orElseThrow(() -> new ResourceNotFoundException());
		Optional.ofNullable(orderedDrinkService.findOne(orderedDrink))
			.orElseThrow(() -> new ResourceNotFoundException());
		reservationService.cancelOrderedDrink(orderObject, orderedDrink);
	}
	
	@PutMapping(params={"reservation", "email"})
	@ResponseStatus(HttpStatus.OK)
	public void cancelReservation(@PathParam("reservation") Long reservation, 
			@PathParam("email") String email){
		Reservation reservationObject = Optional.ofNullable(reservationService.findOne(reservation))
				.orElseThrow(() -> new ResourceNotFoundException());
		reservationService.cancelReservation(reservationObject,email);
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


	@GetMapping(params={"restaurant", "date", "time", "duration"})
	@ResponseStatus(HttpStatus.OK)
	public List<RestaurantTable> findFreeTables(@PathParam("restaurant") Long restaurant,
			@PathParam("date") Date date, @PathParam("time") Time time,
			@PathParam("duration") Integer duration){
		
		return reservationService.findFreeTables(restaurant, date, time, duration);
	}
	@GetMapping(path = "/findByRestaurantId/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Visit> findByRestaurantId(@PathVariable Long id){		
		return reservationService.reservationsByRestaurant(id);
	}
	
}



