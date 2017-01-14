package com.app.drink;

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
@Entity
@Table(name = "DRINK")
public class Drink {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DRINK_ID")
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotNull
	private BigDecimal price;		
	
}
