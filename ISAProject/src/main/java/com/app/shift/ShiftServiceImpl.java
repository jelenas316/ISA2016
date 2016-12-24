package com.app.shift;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ShiftServiceImpl implements ShiftService{
	
	private final ShiftRepository shiftRepo;
	
	@Autowired
	public ShiftServiceImpl(ShiftRepository shiftRepo){
		this.shiftRepo = shiftRepo;
	}
	
	@Override
	public Iterable<Shift> findAll(){
		return shiftRepo.findAll();
	}
	
	@Override
	public Shift findOne(Long id){
		return shiftRepo.findOne(id);
	}
	
	public Shift save(Shift shift){
		return shiftRepo.save(shift);
	}
	
	@Override
	public void delete(Long id) {
		shiftRepo.delete(id);
	}

}
