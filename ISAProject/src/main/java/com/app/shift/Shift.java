package com.app.shift;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.app.bartender.Bartender;
import com.app.cook.Cook;
import com.app.reon.Reon;
import com.app.waiter.Waiter;

import lombok.Data;

@Data
@Table(name = "shift")
@Entity
public class Shift {

	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SHIFT_ID")
	private Long id;
	
	@NotNull
	private Time beginOfShift;

	@NotNull
	private Time endOfShift;
	
	@NotNull
	private Date datum;
	
//	private Waiter waiters;
//	
//	@OneToMany
//	private Cook cooks;
//	
//	@OneToMany
//	private Bartender bartenders;
	
//	@NotNull
//	private Reon reon;
	
}

