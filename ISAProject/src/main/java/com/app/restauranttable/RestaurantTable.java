package com.app.restauranttable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.app.restaurant.Restaurant;


@Table(name = "RESTAURANT_TABLE")
@Entity
public class RestaurantTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RESTAURANT_TABLE_ID")
	private Long id;
	
	@NotNull
	private Integer number;
	
	@NotBlank
	@Enumerated(EnumType.STRING)
	private Segment position;
	

	//private Restaurant restaurant;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Segment getPosition() {
		return position;
	}

	public void setPosition(Segment position) {
		this.position = position;
	}

	/*@ManyToOne
	@JoinColumn(name = "ID_RESTAURANT", referencedColumnName = "RESTAURANT_ID", nullable = false)
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}*/
	
}
