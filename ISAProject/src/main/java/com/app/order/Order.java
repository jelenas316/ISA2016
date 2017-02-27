package com.app.order;

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

import com.app.guest.Guest;
import com.app.restauranttable.RestaurantTable;

import lombok.Data;

@Data
@Table(name = "order_list")
@Entity
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_ID")
	private Long id;
	

//	@NotNull
	@ManyToOne
	@JoinColumn(name = "RESTAURANT_TABLE")
	private RestaurantTable table;
	
//	@NotNull
	@ManyToOne
	@JoinColumn(name = "GUEST")
	private Guest guest;

	@OneToMany
	private List<OrderedDrink> drinks;
	
	@OneToMany
	private List<OrderedFood> food;
	
}
