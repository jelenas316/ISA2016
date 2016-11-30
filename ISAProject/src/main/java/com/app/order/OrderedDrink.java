package com.app.order;

import javax.validation.constraints.NotNull;

import com.app.FoodAndDrinkStatus;
import com.app.drink.Drink;
import com.app.worker.Worker;

import lombok.Data;

@Data
//@Entity
//@Table(name = "ordered_drink")
public class OrderedDrink {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "ORDERED_DRINK_ID")
	private Long id;

	@NotNull
	private Integer quantity;
	
	@NotNull
	private Drink drink;
	
	@NotNull
	private FoodAndDrinkStatus foodStatus;
	
	//koji sanker je prihvatio
	private Worker worker;
}
