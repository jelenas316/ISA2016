package com.app.grade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.app.restaurant.Restaurant;
import com.app.user.User;

import lombok.Data;

//@Data
//@Entity
//@Table(name = "grade")
public class Grade {
	
//	@Id
	private long id;
	
//	@Column(name = "grade_value", nullable=false)
	private int value;
	
//	@ManyToOne
//	@JoinColumn(name = "grade_restaurant", referencedColumnName = "restaurant_id", nullable = true)
//	private Restaurant restaurant;
	
//	@ManyToOne
//	@JoinColumn(name = "grade_guest", referencedColumnName = "email", nullable = true)
	private User guest;
}
