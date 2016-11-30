package com.app.drinkCard;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import com.app.drink.Drink;

import lombok.Data;

@Data
//@Table(name = "drink_card")
//@Entity
public class DrinkCard {
	
//	@Id	
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "DRINK_CARD_ID")
	private int id;
	
	@NotNull
	private ArrayList<Drink> drinkCard;

}
