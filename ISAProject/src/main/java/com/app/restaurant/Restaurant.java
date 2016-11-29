package com.app.restaurant;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.app.dish.Dish;
import com.app.drink.Drink;

import lombok.Data;

@Data
@Table(name = "restaurant")
@Entity
public class Restaurant {

	@NotBlank
	private String name;
	@NotBlank
	private String description;
	private ArrayList<Dish> menu;
	private ArrayList<Drink> drinkCard;
	
	
}
