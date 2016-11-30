package com.app.grade;

import javax.validation.constraints.NotNull;

import com.app.food.Food;
import com.app.restaurant.Restaurant;
import com.app.user.User;
import com.app.worker.Worker;

import lombok.Data;

@Data
//@Entity
//@Table(name = "grade")
public class Grade {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "GRADE_ID")
	private Long id;
	
//	@Column(name = "grade_value")
	@NotNull
	private Integer value;
	
	private Restaurant restourant;
	
	private Food food;
	
	private Worker worker;

	private User guest;
	
//	@ManyToOne
//	@JoinColumn(name = "grade_restaurant", referencedColumnName = "restaurant_id", nullable = true)
//	private Restaurant restaurant;
	
//	@ManyToOne
//	@JoinColumn(name = "grade_guest", referencedColumnName = "email", nullable = true)
//	private User guest;
	
}
