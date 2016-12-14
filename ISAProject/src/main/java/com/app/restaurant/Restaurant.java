package com.app.restaurant;

import org.hibernate.validator.constraints.NotBlank;

import com.app.configuration.Configuration;
import com.app.drinkCard.DrinkCard;
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
	
	private String description;
	
	private Menu menu;

	private DrinkCard drinkCard;
	//private List<Drink> drinkCard;
	
	private Configuration configuration;	
}
