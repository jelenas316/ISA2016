package com.app.order;

import java.util.List;

import com.app.restauranttable.RestaurantTable;

import lombok.Data;

@Data
//@javax.persistence.Table(name = "order")
//@Entity
public class Order {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "ORDER_ID")
	private Long id;
	
	private RestaurantTable table;
	
	private List<OrderedDrink> drinks;
	
	private List<OrderedFood> food;
	
}
