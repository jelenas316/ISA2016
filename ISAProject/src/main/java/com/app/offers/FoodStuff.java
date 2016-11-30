package com.app.offers;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
//@Entity
//@Table(name = "food_stuff")
public class FoodStuff {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "FOOD_STUFF_ID")
	private Long id;
	
	@NotBlank
	private String name;

	private String description;
	
}
