package com.app.waiter;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.drink.Drink;
import com.app.order.Order;
import com.app.order.OrderRepository;
import com.app.order.OrderedDrink;
import com.app.order.OrderedDrinkRepository;
import com.app.order.OrderedFood;
import com.app.order.OrderedFoodRepository;
import com.app.restaurant.Restaurant;
import com.app.restaurant.RestaurantRepository;
import com.app.shift.Shift;
import com.app.shift.ShiftDTO;
import com.app.shift.ShiftRepository;

@Service
@Transactional
public class WaiterServiceImpl implements WaiterService {
	
	private final WaiterRepository waiterRepo;
	private final ShiftRepository shiftRepo;
	private final RestaurantRepository restaurantRepo;
	private final OrderedFoodRepository orderedFoodRepo;
	private final OrderRepository orderRepo;
	private final OrderedDrinkRepository orderedDrinkRepo;
	
	@Autowired
	public WaiterServiceImpl(final WaiterRepository waiterRepo, final ShiftRepository shiftRepo, final RestaurantRepository restaurantRepo, 
			final OrderedFoodRepository orderedFoodRepo, final OrderRepository orderRepo, final OrderedDrinkRepository orderedDrinkRepo){
		this.waiterRepo = waiterRepo;
		this.shiftRepo= shiftRepo;
		this.restaurantRepo = restaurantRepo;
		this.orderedFoodRepo = orderedFoodRepo;
		this.orderRepo = orderRepo;
		this.orderedDrinkRepo = orderedDrinkRepo;
	}
	
	@Override
	public Iterable<Waiter> findByRestaurant(Restaurant restaurant) {
		return waiterRepo.findByRestaurant(restaurant);
	}
	
	@Override
	public Waiter findOne(String email) {
		Waiter w = waiterRepo.findOne(email);
		return w;
	}
	
	@Override
	public Waiter save(Waiter waiter) {
		return waiterRepo.save(waiter);
	}
	
	@Override
	public void delete(String email) {
		waiterRepo.delete(email);
	}

	@Override
	public void deleteShift(String email, Long id) {
		Waiter w = waiterRepo.findOne(email);
		for (int i = 0; i < w.getShifts().size(); i++) {
			if(w.getShifts().get(i).getId() == id){
				w.getShifts().remove(i);
				waiterRepo.save(w);
				shiftRepo.delete(id);
			}
		}		
	}

	@Override
	public boolean addShift(ShiftDTO shift) {
		Waiter w = waiterRepo.findOne(shift.getWorker());
		List<Shift> shifts = w.getShifts();
		if(shift.getId() == -1){
			shift.setId(null);
		}
		Shift sh = createShift(shift);
		if(shift.getReon().size()!=0){
			sh.setReon(shift.getReon());
		}
		try {
			for (int i = 0; i < shifts.size(); i++) {		
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String dateStart = sdf.format(shifts.get(i).getStartDate());
				String dateEnd= sdf.format(shifts.get(i).getEndDate());	
				Date shiftDateStart = sdf.parse(dateStart);
				Date shiftDateEnd= sdf.parse(dateEnd);
				shiftDateEnd = addDays(shiftDateEnd, 1);
				if((sh.getEndDate().before(shiftDateStart)
						|| sh.getStartDate().after(shiftDateEnd)) || shifts.get(i).getId()==shift.getId()){
					continue;					
				}else{
					return false;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(shift.getId()!=null){
			for (int i =0; i< shifts.size(); i++) {
				if(shifts.get(i).getId() == shift.getId()){
					w.getShifts().set(i, sh);
				}
			}
		}else{
			w.getShifts().add(sh);
		}
		shiftRepo.save(sh);		
		waiterRepo.save(w);
		return true;
		
	}
	private static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); 
        return cal.getTime();
    }
	
	private Shift createShift(ShiftDTO shift){
		int startHours = shift.getBeginOfShift() / 60;
		int startMinutes = shift.getBeginOfShift() % 60; 
		int endHours = shift.getEndOfShift() / 60;
		int endMinutes = shift.getEndOfShift() % 60; 
		
		Time beginOfShift = Time.valueOf(String.valueOf((startHours < 10 ? "0" : "") + startHours)+
				":"+String.valueOf((startMinutes< 10 ? "0" : "") + startMinutes)+":00");
		Time endOfShift = Time.valueOf(String.valueOf((endHours < 10 ? "0" : "") 
				+ endHours)+":"+String.valueOf((endMinutes< 10 ? "0" : "") + endMinutes)+":00");
		Shift s = new Shift();
		if(shift.getId()!=null){
			s.setId(shift.getId());
		}
		s.setBeginOfShift(beginOfShift);
		s.setEndOfShift(endOfShift);
		s.setStartDate(shift.getStartDate());
		s.setEndDate(shift.getEndDate());
		return s;
	}

	@Override
	public void cancelFood(Order order, Long orderedFoodId) {
		// TODO Auto-generated method stub
		Stream<OrderedFood> ordered = order.getFood().stream()
				.filter(food ->  food.getId().equals(orderedFoodId));
		order.getFood().remove(ordered);
		orderRepo.save(order);
		orderedFoodRepo.delete(orderedFoodId);
		
	}

	@Override
	public void cancelDrink(Order order, Long orderedDrinkId) {
		// TODO Auto-generated method stub
		
		Stream<OrderedDrink> ordered = order.getDrinks().stream().filter(Drink -> Drink.getId().equals(orderedDrinkId));
		order.getDrinks().remove(ordered);
		orderRepo.save(order);
		orderedDrinkRepo.delete(orderedDrinkId);
		
	}
}
