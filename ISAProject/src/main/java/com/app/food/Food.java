package com.app.food;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.app.restaurant.Restaurant;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
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
