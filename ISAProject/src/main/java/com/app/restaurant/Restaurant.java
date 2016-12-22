package com.app.restaurant;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.app.drink.Drink;
import com.app.food.Food;
import com.app.restaurantmanager.RestaurantManager;
import com.app.restauranttable.RestaurantTable;

import lombok.Data;

@Data
@Table(name = "restaurant")
@Entity
public class Restaurant {

	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RESTAURANT_ID")
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;

	@OneToMany(mappedBy="restaurant")
	private Set<Food> menu;
	
	@OneToMany(mappedBy="restaurant")
	private Set<Drink> drinkCard;
	
	@OneToMany(mappedBy="restaurant")
	private Set<RestaurantTable> tables;
	
}
