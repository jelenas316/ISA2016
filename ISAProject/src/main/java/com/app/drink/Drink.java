package com.app.drink;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
//@Entity
//@Table(name = "drink")
public class Drink {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "DRINK_ID")
	private int id;
	
	@NotBlank
	private String name;
	
	private String description;
	
	@NotNull
	private BigDecimal price;
}
