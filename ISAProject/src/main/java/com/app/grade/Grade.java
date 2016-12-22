package com.app.grade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.app.guest.Guest;
import com.app.restaurant.Restaurant;

import lombok.Data;

@Data
@Entity
@Table(name = "grade")
public class Grade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GRADE_ID")
	private Long id;
	
	@NotNull
	@Max(5)
	@Min(0)
	@Column(name="GRADE_VALUE")
	private Integer value;

	@ManyToOne
	@JoinColumn(name = "RESTAURANT")
	private Restaurant restaurant;
	
//	private Food food;
	
//	private Worker worker;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "GUEST")
	private Guest guest;

}
