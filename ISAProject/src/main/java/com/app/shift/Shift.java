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

import com.app.restauranttable.RestaurantTable;
import com.app.waiter.Waiter;
import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private Time beginOfShift;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private Time endOfShift;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone = "Europe/Budapest")
	private Date startDate;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Budapest")
	private Date endDate;
	
	@OneToMany
	private List<RestaurantTable> reon;	
	
}

