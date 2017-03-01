package com.app.restaurant;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotBlank;

import com.app.drink.Drink;
import com.app.food.Food;
import com.app.restauranttable.RestaurantTable;

import lombok.Data;

@Table(name = "restaurant")
@Entity
@Data
public class Restaurant {

	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RESTAURANT_ID")
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String address;
	
	@OneToMany
	private List<Drink> drinks ;
	
	@OneToMany
	private List<Food> menu ;
	
	@OneToMany
	@NotFound(action=NotFoundAction.IGNORE)
	private List<RestaurantTable> tables;

}
