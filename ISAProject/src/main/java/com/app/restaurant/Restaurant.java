package com.app.restaurant;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.app.drink.Drink;
import com.app.menu.Menu;

import lombok.Data;

@Data
//@Table(name = "restaurant")
//@Entity
public class Restaurant {

//	@Id	
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "RESTAURANT_ID")
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	private Menu menu;

//	private DrinkCard drinkCard;
	
	private List<Drink> drinkCard;
	
	
}
