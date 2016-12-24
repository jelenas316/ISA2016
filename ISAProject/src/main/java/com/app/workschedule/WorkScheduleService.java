package com.app.workschedule;

public interface WorkScheduleService {
	
	Iterable<WorkSchedule> findAll();
	WorkSchedule findOne(Long id);
	WorkSchedule save(WorkSchedule workSchedule);
	void delete(Long id);
 
}
