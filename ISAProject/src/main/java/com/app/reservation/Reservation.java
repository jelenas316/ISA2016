package com.app.reservation;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.app.order.Order;
import com.app.restaurant.Restaurant;
import com.app.table.Table;
import com.app.user.User;

import lombok.Data;

@Data
//@Entity
//@javax.persistence.Table(name = "reservation")
public class Reservation {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "RESERVATION_ID")
	private Long id;

	@NotNull
	private Date arrival;
	
	private Integer duration;
	
	@NotNull
	private List<Table> table;
	
	@NotNull
	private Restaurant restaurant;
	
	//onaj ko je rezervisao i prijatelji koji su prihvatili poziv
	@NotNull
	private List<User> guests;
	
	//lista porudzbina
	private List<Order> orders;
}
