package com.app.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.app.FoodAndDrinkStatus;
import com.app.cook.Cook;
import com.app.food.Food;

import lombok.Data;


@Data
@Entity
@Table(name = "ordered_food")
public class OrderedFood {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDERED_FOOD_ID")
	private Long id;

	@NotNull
	private Integer quantity;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "FOOD")
	private Food food;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="FOOD_STATUS")
	private FoodAndDrinkStatus foodStatus;
	
	//koji kuvar je prihvatio
	@ManyToOne
	@JoinColumn(name = "COOK")
	private Cook cook;

}
