package com.app.foodStuff;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.app.restaurant.Restaurant;
import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "food_stuff")
public class FoodStuff {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FOOD_STUFF_ID")
	private Long id;
	
	@NotBlank
	private String name;

	private String description;
	
	@NotNull
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "RESTAURANT")
	private Restaurant restaurant;
	
}
