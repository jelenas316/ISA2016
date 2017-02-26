package com.app.reservation;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.app.guest.Guest;
import com.app.order.Order;
import com.app.restaurant.Restaurant;
import com.app.restauranttable.RestaurantTable;

import lombok.Data;

@Data
@Entity
@Table(name = "reservation")
public class Reservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RESERVATION_ID")
	private Long id;

	@NotNull
	private Date arrival;
	
	@NotNull
	@Column(name = "ARRIVAL_TIME")
	private Time arrivalTime;
	
	@NotNull
	private Integer duration;
	
//	@NotNull
	@OneToMany
	@Column(name="TABLES")
	private List<RestaurantTable> tables;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "RESTAURANT")
	private Restaurant restaurant;
	
	//onaj ko je rezervisao i prijatelji koji su prihvatili poziv
//	@NotNull
	@OneToMany
	private List<Guest> guests;
	
	@OneToMany
	@Column(name="INVITED_FRIENDS")
	private List<Guest> invitedFriends;
	
	//lista porudzbina
	@OneToMany
	private List<Order> orders;
	
}
