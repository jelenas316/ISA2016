package com.app.workschedule;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkScheduleRepository extends CrudRepository<WorkSchedule, Long>{

}
