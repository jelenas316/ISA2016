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
import com.app.bartender.Bartender;
import com.app.cook.Cook;
import com.app.drink.Drink;

import lombok.Data;

@Data
@Entity
@Table(name = "ordered_drink")
public class OrderedDrink {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDERED_DRINK_ID")
	private Long id;

	@NotNull
	private Integer quantity;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "DRINK")
	private Drink drink;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="FOOD_STATUS")
	private FoodAndDrinkStatus foodStatus;
	
	//koji sanker je prihvatio
	@ManyToOne
	@JoinColumn(name = "BARTENDER")
	private Bartender bartender;

}
