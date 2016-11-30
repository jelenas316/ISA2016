package com.app.menu;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import com.app.food.Food;

import lombok.Data;

@Data
//@Table(name = "menu")
//@Entity
public class Menu {
	
//	@Id	
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "MENU_ID")
	private int id;
	
	@NotNull
	private ArrayList<Food> food;
}