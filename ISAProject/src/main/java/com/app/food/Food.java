package com.app.food;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
@Data
@Table(name = "FOOD")
@Entity
public class Food {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FOOD_ID")
	private Long id;
	
	@NotBlank
	private String name;

	@NotBlank
	private String description;
	
	@NotNull
	private BigDecimal price;
	
	//private Restaurant restaurant;

/*
	@ManyToOne
	@JoinColumn(name = "ID_RESTAURANT", referencedColumnName = "RESTAURANT_ID", nullable = false)
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}*/

	
}
