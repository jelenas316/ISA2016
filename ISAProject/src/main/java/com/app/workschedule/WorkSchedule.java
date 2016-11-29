package com.app.workschedule;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.reon.Reon;
import com.app.worker.Worker;

import lombok.Data;

@Data
@Entity
@Table(name = "workSchedule")
public class WorkSchedule {

	private Date begin;
	private Date end;
	private Worker worker;
	private Reon reon;
}
