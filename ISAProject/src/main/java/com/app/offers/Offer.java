package com.app.offers;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.OneToMany;

import com.app.bidder.Bidder;
import com.app.drink.Drink;

import lombok.Data;

@Data
//@Entity
//@Table(name = "offer")
public class Offer {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "OFFER_ID")
	private Long id;
	
	private BigDecimal totalPrice;
	
	private Integer deliveryTime;
	
	private Integer guarantee;
	
	private Map<Drink, Integer> drink;
	
	private Map<FoodStuff, BigDecimal> food;
	
	private boolean accepted;
	
	@OneToMany
	private Bidder bidder;
	
}
