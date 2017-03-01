package com.app.offers;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.app.bidder.Bidder;
import com.app.grocerylist.GroceryList;

import lombok.Data;

@Data
@Entity
@Table(name = "offer")
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OFFER_ID")
	private Long id;
	
	private BigDecimal totalPrice;
	
	private Integer deliveryTime;
	
	private Integer guarantee;
	
	@OneToMany
	private List<DrinkOffer> drinks;
	
	@OneToMany
	private List<FoodStuffOffer> food;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private OfferStatus accepted;
	
	@ManyToOne
	@JoinColumn(name = "GROCERY_LIST")
	private GroceryList groceryList;
	
	@ManyToOne
	@JoinColumn(name = "BIDDER")
	private Bidder bidder;
}
