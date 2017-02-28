package com.app.grocerylist;

import java.util.Date;
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
import javax.validation.constraints.NotNull;

import com.app.offers.DrinkOffer;
import com.app.offers.FoodStuffOffer;
import com.app.restaurant.Restaurant;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "grocery_list")
public class GroceryList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GROCERY_LIST_ID")
	private Long id;
	
	@NotNull
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone = "Europe/Budapest")
	private Date startDate;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone = "Europe/Budapest")
	private Date endDate;

	@OneToMany
	private List<DrinkOffer> drinks;

	@OneToMany
 	private List<FoodStuffOffer> food;

	@NotNull
 	private boolean accepted;
	
	@ManyToOne
	@JoinColumn(name = "RESTAURANT")
	private Restaurant restaurant;
}
