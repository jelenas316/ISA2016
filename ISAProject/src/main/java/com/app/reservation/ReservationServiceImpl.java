package com.app.reservation;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.app.guest.Guest;
import com.app.order.Order;
import com.app.order.OrderRepository;
import com.app.order.OrderedDrink;
import com.app.order.OrderedDrinkRepository;
import com.app.order.OrderedFood;
import com.app.order.OrderedFoodRepository;
import com.app.restaurant.Restaurant;
import com.app.restaurant.RestaurantRepository;
import com.app.restauranttable.RestaurantTable;
import com.app.restauranttable.Status;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{

	private final ReservationRepository reservationRepo;
	private final OrderRepository orderRepo;
	private final OrderedDrinkRepository orderedDrinkRepo;
	private final OrderedFoodRepository orderedFoodRepo;
	private final RestaurantRepository restaurantRepo;
	
	@Autowired
	public ReservationServiceImpl(final ReservationRepository reservationRepo,
			final OrderRepository orderRepo, final OrderedDrinkRepository orderedDrinkRepo,
			final OrderedFoodRepository orderedFoodRepo, final RestaurantRepository restaurantRepo) {
		this.reservationRepo=reservationRepo;
		this.orderRepo = orderRepo;
		this.orderedDrinkRepo = orderedDrinkRepo;
		this.orderedFoodRepo = orderedFoodRepo;
		this.restaurantRepo = restaurantRepo;
	}
	
	@Override
	public Iterable<Reservation> findAll() {
		return reservationRepo.findAll();
	}

	@Override
	public Reservation findOne(Long id) {
		return reservationRepo.findOne(id);
	}

	@Override
	public Reservation save(Reservation reservation) {
		return reservationRepo.save(reservation);
	}

	@Override
	public void delete(Long id) {
		reservationRepo.delete(id);
	}


	@Override
	public List<Reservation> findFutureReservationsForGuest(String email) {
		return getByEmail(email, findReservationByDate(reservationRepo.findAll(),true));
	}

	@Override
	public Iterable<Reservation> findPreviousByGuest(String email) {
		return getByEmail(email, findReservationByDate(reservationRepo.findAll(),false));
	}
	
	public void cancelOrderedDrink(Order order, Long orderedDrinkId){
		Stream<OrderedDrink> ordered = order.getDrinks().stream()
				.filter(drink ->  drink.getId().equals(orderedDrinkId));
		order.getDrinks().remove(ordered);
		orderRepo.save(order);
		orderedDrinkRepo.delete(orderedDrinkId);
	}
	
	public void cancelOrderedFood(Order order, Long orderedFoodId){
		Stream<OrderedFood> ordered = order.getFood().stream()
				.filter(food ->  food.getId().equals(orderedFoodId));
		order.getFood().remove(ordered);
		orderRepo.save(order);
		orderedFoodRepo.delete(orderedFoodId);
	}
	
	public void cancelReservation(Reservation reservation, String email){
		Guest guestObject = reservation.getGuests().stream().filter(guest -> guest.getEmail().equals(email))
				.findFirst().get();
		reservation.getGuests().remove(guestObject);
		Optional<Order> order = reservation.getOrders().stream()
				.filter(or -> or.getGuest().getEmail().equals(email)).findFirst();
		if (order.isPresent()) {
			reservation.getOrders().remove(order.get());
			orderRepo.delete(order.get().getId());
			for(OrderedDrink drink : order.get().getDrinks())
				orderedDrinkRepo.delete(drink.getId());
			for(OrderedFood food : order.get().getFood())
				orderedFoodRepo.delete(food.getId());
		}
		if (reservation.getGuests().size() == 0 && reservation.getInvitedFriends().size() == 0)
			reservationRepo.delete(reservation);

	}
	
	/**
	 * Method that returns all reservations where guest was.
	 * 
	 * @param email - email of the guest
	 * @param reservations - reservations for search
	 */
	public List<Reservation> getByEmail(String email,Iterable<Reservation> reservations){
		List<Reservation> returnValue=new ArrayList<>();
		for(Reservation reservation : reservations){
			for(Guest guest : reservation.getGuests()){
				if(guest.getEmail().equals(email))
					returnValue.add(reservation);
			}
		}
		
		return returnValue;
	}
	
	/**
	 * If param 'future' is true, method returns all reservations witch hasn't been yet, 
	 * if false, returns previous resrvations.
	 * 
	 * @param reservations - all reservations
	 * @param future - true if future, false if past
	 */
	public List<Reservation> findReservationByDate(Iterable<Reservation> reservations, Boolean future){
		List<Reservation> returnValue=new ArrayList<>();
		Date today = new Date(Calendar.getInstance().getTime().getTime());
		Time time = new Time(today.getTime());
		for(Reservation reservation : reservations){
			if(today.toString().equals(reservation.getArrival().toString())){
				if(isBefore(time.toString(), reservation.getArrivalTime().toString())){
					if(future)
						returnValue.add(reservation);
				}else{
					if(!future)
						returnValue.add(reservation);
				}
			}else if(today.compareTo(reservation.getArrival())<0 && future){
					returnValue.add(reservation);
			}else if(today.compareTo(reservation.getArrival())>0 && !future){
					returnValue.add(reservation);
			}
		}
		return returnValue;
	}
	

	/**
	 * Check if the "time" is before the "time2".
	 */
	public Boolean isBefore(String time1, String time2){
		Integer time1Hours = getNumber(time1);
		Integer time1Minutes = getNumber(time1.substring(3, time1.length()));
		Integer time2Hours = getNumber(time2);
		Integer time2Minutes = getNumber(time2.substring(3, time2.length()));
		if(time1Hours<time2Hours){
			return true;
		}else if(time1Hours==time2Hours){
			if(time1Minutes<time2Minutes)
				return true;
		}
		
		return false;
	}

	/**
	 * Returns first number in string.
	 */
	public Integer getNumber(String time){
		Integer index = time.indexOf(':');
		String value = time.substring(0, index);
		return Integer.parseInt(value);
	}

	@Override
	public List<RestaurantTable> findFreeTables(Long restaurantId, Date date, Time time, Integer duration) {
		Restaurant restaurant = Optional.ofNullable(restaurantRepo.findOne(restaurantId))
				.orElseThrow(() -> new ResourceNotFoundException());
		List<RestaurantTable> tables = restaurant.getTables();
		List<RestaurantTable> reservedTables = new ArrayList<RestaurantTable>();
		
		List<Reservation> reservations = reservationRepo.findByRestaurant(restaurant);
		for(Reservation reservation : reservations){
			if(reservation.getArrival().equals(date) && isReserved(time, duration, reservation)){
				reservation.getTables().forEach(table -> reservedTables.add(table));
			}
		}
		
		for(RestaurantTable table : tables){
			if(reservedTables.contains(table)){
				table.setStatus(Status.RESERVED);
			}else
				table.setStatus(Status.FREE);
		}
		return tables;
	}
	
	/**
	 * Method that returns true if the table is reserved.
	 */
	private boolean isReserved(Time time, Integer duration, Reservation reservation){
		String timeString = time.toString();
		Integer newArrivalHours  = Integer.parseInt(timeString.substring(0, 2));
		Integer newArrivalMinutes  = Integer.parseInt(timeString.substring(3, 5));
		Integer newArrival = newArrivalHours * 60 + newArrivalMinutes;
		Integer newEnd = newArrival + duration;
		
		String reservationTime = reservation.getArrivalTime().toString();
		Integer reservationArrivalHours = Integer.parseInt(reservationTime.substring(0, 2));
		Integer reservationArrivalMinutes = Integer.parseInt(reservationTime.substring(3, 5));
		Integer reservationArrival = reservationArrivalHours * 60 + reservationArrivalMinutes;
		Integer reservationEnd = reservationArrival + reservation.getDuration();
		
		if((newArrival>=reservationArrival && newArrival<=reservationEnd)
				|| (newEnd>=reservationArrival && newEnd<=reservationEnd)){
			return true;
		}
		return false;
	}

	@Override
	public List<Visit> reservationsByRestaurant(Long id) {
		List<Reservation> byRestaurant =  reservationRepo.findByRestaurantId(id);
		List<Visit> visits = new ArrayList<Visit>();
		for (Reservation reservation : byRestaurant) {
			Visit visit = new Visit();
			int numberOfGuests = 0;
			List<Reservation> byDate =  reservationRepo.findByArrival(reservation.getArrival());
			visit.setDate(reservation.getArrival());
			for (Reservation reservation2 : byDate) {
				numberOfGuests+=reservation2.getGuests().size();
			}
			visit.setGuests(numberOfGuests);
			visits.add(visit);
		}
		return visits;
	}
}
