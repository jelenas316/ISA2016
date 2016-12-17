package com.app.restaurant;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.app.drink.Drink;
import com.app.food.Food;

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

//	@OneToMany
//	private List<Food> menu;
//	
//	@OneToMany
//	private List<Drink> drinkCard;
	
}
