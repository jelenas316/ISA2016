package com.app.order;

import javax.validation.constraints.NotNull;

import com.app.FoodAndDrinkStatus;
import com.app.food.Food;
import com.app.worker.Worker;

import lombok.Data;


@Data
//@Entity
//@Table(name = "ordered_food")
public class OrderedFood {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "ORDERED_FOOD_ID")
	private Long id;

	@NotNull
	private Integer quantity;
	
	@NotNull
	private Food food;
	
	@NotNull
	private FoodAndDrinkStatus foodStatus;
	
	//koji kuvar je prihvatio
	private Worker worker;
}
