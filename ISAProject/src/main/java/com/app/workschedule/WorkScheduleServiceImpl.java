package com.app.workschedule;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WorkScheduleServiceImpl implements WorkScheduleService{
	
	private final WorkScheduleRepository wcRepo;
	
	@Autowired
	public WorkScheduleServiceImpl(WorkScheduleRepository wcRepo){
		this.wcRepo = wcRepo;
	}
	
	@Override
	public Iterable<WorkSchedule> findAll(){
		return wcRepo.findAll();
	}
	
	@Override 
	public WorkSchedule findOne(Long id){
		return wcRepo.findOne(id);
	}
	
	@Override
	public WorkSchedule save(WorkSchedule wc){
		return wcRepo.save(wc);
	}
	
	@Override
	public void delete(Long id){
		wcRepo.delete(id);
	}
	
	

}
