package com.app.workschedule;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.app.reon.Reon;
import com.app.worker.Worker;

import lombok.Data;

@Data
//@Entity
//@Table(name = "workSchedule")
public class WorkSchedule {

//	@Id
	private int id;
	
	private Date begin;
	private Date end;
	private Worker worker;
	private Reon reon;
}
