package com.app.food;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
//@Table(name = "menu")
//@Entity
public class Food {
	
//	@Id	
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "FOOD_ID")
	private Long id;
	
	@NotBlank
	private String name;

	private String description;
	
	@NotNull
	private BigDecimal price;

}
