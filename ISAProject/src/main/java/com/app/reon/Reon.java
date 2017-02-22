package com.app.reon;

import java.util.ArrayList;

import com.app.restauranttable.RestaurantTable;

import lombok.Data;

@Data
//@Entity
//@javax.persistence.Table(name = "reon")
public class Reon {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "REON_ID")
//	private Long id;
	
	private ArrayList<RestaurantTable> tables;

}
