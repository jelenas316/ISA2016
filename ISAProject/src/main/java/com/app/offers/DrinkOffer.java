package com.app.offers;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Data;
@Data
@Entity
@Table(name = "drink_offer")
public class DrinkOffer {
	@Id
	@Column(name = "DRINK_OFFER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private Long drinkGrocery;
	
	@NotBlank
	private String name;

	@NotBlank
	private String description;
	
	@NotNull
	private int quantity;

	private BigDecimal price;
}
