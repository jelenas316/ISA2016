package com.app.offer;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.drink.Drink;
import com.app.drink.FoodStuff;

import lombok.Data;

@Data
@Entity
@Table(name = "Offer")
public class Offer {
	
	private Date expires;
	private ArrayList<Drink> drinks;
 	private ArrayList<FoodStuff> foodStuff;
 	private boolean accepted;

}
