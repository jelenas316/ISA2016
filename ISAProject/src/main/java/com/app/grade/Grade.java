package com.app.grade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.app.restaurant.Restaurant;
import com.app.users.User;

import lombok.Data;

@Data
@Entity
@Table(name = "grade")
public class Grade {
	private long id;
	
	@Column(name = "grade_value", nullable=false)
	private int value;
	
	@ManyToOne
	@JoinColumn(name = "grade_restaurant", referencedColumnName = "restaurant_id", nullable = true)
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "grade_guest", referencedColumnName = "user_id", nullable = true)
	private User guest;
}
