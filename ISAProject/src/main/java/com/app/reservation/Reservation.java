package com.app.reservation;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.restaurant.Restaurant;

import lombok.Data;

@Data
@Entity
@Table(name = "reservation")
public class Reservation {

	private long id;

	private Date arrival;
	
	private int duration;
	
	private Table table;
	
	private Restaurant restaurant;
}
