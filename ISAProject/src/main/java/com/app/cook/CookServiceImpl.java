package com.app.cook;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.restaurant.Restaurant;
import com.app.shift.Shift;
import com.app.shift.ShiftDTO;
import com.app.shift.ShiftRepository;

@Service
@Transactional
public class CookServiceImpl implements CookService {
	
	private final CookRepository cookRepo;
	private final ShiftRepository shiftRepo;
	
	@Autowired
	public CookServiceImpl(final CookRepository cookRepo, final ShiftRepository shiftRepo) {
		this.cookRepo = cookRepo;
		this.shiftRepo=shiftRepo;
	}
	
	@Override
	public Iterable<Cook> findAll() {
		return cookRepo.findAll();
	}
	
	@Override
	public Cook findOne( String email) {
		return cookRepo.findOne(email);
	}
	
	@Override
	public Cook save(Cook cook) {
		return cookRepo.save(cook);
	}
	
	@Override
	public void delete(String email){
		cookRepo.delete(email);
	}
	@Override
	public void deleteShift(String email, Long id) {
		Cook w = cookRepo.findOne(email);
		for (int i = 0; i < w.getShifts().size(); i++) {
			if(w.getShifts().get(i).getId() == id){
				w.getShifts().remove(i);
				cookRepo.save(w);
				shiftRepo.delete(id);
			}
		}		
	}

	@Override
	public boolean addShift(ShiftDTO shift) {
		Cook w = cookRepo.findOne(shift.getWorker());
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
						|| sh.getStartDate().after(shiftDateEnd))|| shifts.get(i).getId()==shift.getId()){
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
		cookRepo.save(w);
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
	public Iterable<Cook> findByRestaurant(Restaurant restaurant) {
		return cookRepo.findByRestaurant(restaurant);
	}

}
