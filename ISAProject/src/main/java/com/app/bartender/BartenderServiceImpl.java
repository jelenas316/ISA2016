package com.app.bartender;

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
public class BartenderServiceImpl implements BartenderService{
	
	private final BartenderRepository bartenderRepo;
	private final ShiftRepository shiftRepo;
	
	@Autowired
	public BartenderServiceImpl(final BartenderRepository baretnderRepo,  final ShiftRepository shiftRepo){
		this.bartenderRepo = baretnderRepo;
		this.shiftRepo = shiftRepo;
	}
	
	@Override
	public Iterable<Bartender> findAll() {
		return bartenderRepo.findAll();
	}
	
	@Override
	public Bartender findOne(String email){
		return bartenderRepo.findOne(email);
	}
	
	@Override
	public Bartender save(Bartender bartender){
		return bartenderRepo.save(bartender);
	}
	
	@Override
	public void delete(String email){
		bartenderRepo.delete(email);
	}

	@Override
	public Iterable<Bartender> findByRestaurant(Restaurant restaurant) {
		return bartenderRepo.findByRestaurant(restaurant);
	}

	@Override
	public void deleteShift(String email, Long shift) {
		Bartender b = bartenderRepo.findOne(email);
		for (int i = 0; i < b.getShifts().size(); i++) {
			if(b.getShifts().get(i).getId() == shift){
				b.getShifts().remove(i);
				bartenderRepo.save(b);
				shiftRepo.delete(shift);
			}
		}			
	}

	@Override
	public boolean addShift(ShiftDTO shift) {
		Bartender w = bartenderRepo.findOne(shift.getWorker());
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
		bartenderRepo.save(w);
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
}
