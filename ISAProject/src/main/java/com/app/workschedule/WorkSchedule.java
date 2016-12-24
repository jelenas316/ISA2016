package com.app.workschedule;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.app.shift.Shift;

import lombok.Data;

@Data
@Entity
@Table(name = "work_schedule")
public class WorkSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "WORK_SCHEDULE_ID")
	private Long id;

	//private List<Shift> shifts;
	
	
	
	
//	@NotNull
//	private Date begin;
//	
//	
//	private Date end;
//	private Worker worker;
//	private Reon reon;
}
