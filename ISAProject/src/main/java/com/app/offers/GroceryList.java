package com.app.offers;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.app.drink.Drink;

import lombok.Data;

@Data
//@Entity
//@Table(name = "gorcery_list")
public class GroceryList {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "GROCERY_LIST_ID")
	private Long id;
	
	@NotNull
	private Date startDate;

	@NotNull
	private Date endDate;

	private ArrayList<Drink> drinks;

 	private ArrayList<FoodStuff> food;

	@NotNull
 	private boolean accepted;

}
