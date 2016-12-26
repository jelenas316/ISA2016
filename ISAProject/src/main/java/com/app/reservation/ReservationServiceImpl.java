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
import org.springframework.stereotype.Service;

import com.app.guest.Guest;
import com.app.order.Order;
import com.app.order.OrderRepository;
import com.app.order.OrderedDrink;
import com.app.order.OrderedDrinkRepository;
import com.app.order.OrderedFood;
import com.app.order.OrderedFoodRepository;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{

	private final ReservationRepository reservationRepo;
	private final OrderRepository orderRepo;
	private final OrderedDrinkRepository orderedDrinkRepo;
	private final OrderedFoodRepository orderedFoodRepo;
	
	@Autowired
	public ReservationServiceImpl(final ReservationRepository reservationRepo,
			final OrderRepository orderRepo, final OrderedDrinkRepository orderedDrinkRepo,
			final OrderedFoodRepository orderedFoodRepo) {
		this.reservationRepo=reservationRepo;
		this.orderRepo = orderRepo;
		this.orderedDrinkRepo = orderedDrinkRepo;
		this.orderedFoodRepo = orderedFoodRepo;
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
}
