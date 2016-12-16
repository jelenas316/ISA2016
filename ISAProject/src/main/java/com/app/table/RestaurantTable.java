package com.app.table;

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

import lombok.Data;

@Data
@Entity
@Table(name = "RESTAURANT_TABLE")
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
	
	@ManyToOne
	@JoinColumn(name = "RESTAURANT_TABLE", nullable = false)
	private Restaurant restaurant;
}
