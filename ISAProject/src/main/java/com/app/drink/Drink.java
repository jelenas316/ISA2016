package com.app.drink;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "drink")
public class Drink {
	
	@NotBlank
	private String name;
	@NotBlank
	private String description;
	private BigDecimal price;
}
