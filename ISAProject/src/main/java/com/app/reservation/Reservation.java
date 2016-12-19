package com.app.reservation;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.app.guest.Guest;
import com.app.order.Order;
import com.app.restaurant.Restaurant;
import com.app.table.RestaurantTable;

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
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "yyyy-MM-dd")
	private Date arrival;
	
	private Integer duration;
	
//	@NotNull
	@OneToMany
	@Column(name="RESTAURANT_TABLE")
	private List<RestaurantTable> table;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "RESTAURANT")
	private Restaurant restaurant;
	
	//onaj ko je rezervisao i prijatelji koji su prihvatili poziv
//	@NotNull
	@OneToMany
	private List<Guest> guests;
	
	//lista porudzbina
	@OneToMany
	private List<Order> orders;
}
