package com.app.restaurant;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.app.drink.Drink;
import com.app.menu.Menu;

import lombok.Data;

@Data
//@Table(name = "restaurant")
//@Entity
public class Restaurant {

//	@Id
	private int id;
	
//	@NotBlank
	private String name;
//	@NotBlank
	private String description;
	private Menu menu;
	private ArrayList<Drink> drinkCard;
	
	
}
