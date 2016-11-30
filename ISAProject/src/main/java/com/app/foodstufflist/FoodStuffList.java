package com.app.foodstufflist;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.app.drink.Drink;
import com.app.drink.FoodStuff;

import lombok.Data;

@Data
//@Entity
//@Table(name = "foodstufflist")
public class FoodStuffList {
	
//	@Id
	private int id;
	
	private ArrayList<Drink> drinks;
 	private ArrayList<FoodStuff> foodStuff;
}
