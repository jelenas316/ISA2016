package com.app.order;

import java.util.List;

import com.app.table.Table;

import lombok.Data;

@Data
//@javax.persistence.Table(name = "order")
//@Entity
public class Order {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "ORDER_ID")
	private Long id;
	
	private Table table;
	
	private List<OrderedDrink> drinks;
	
	private List<OrderedFood> food;
	
}
