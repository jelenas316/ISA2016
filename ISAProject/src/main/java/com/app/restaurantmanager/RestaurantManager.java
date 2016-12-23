package com.app.restaurantmanager;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.app.restaurant.Restaurant;
import com.app.user.UserRole;

import lombok.Data;

@Data
@Table(name = "RESTAURANT_MANAGER")
@Entity
public class RestaurantManager {

	@Id
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String name;

	@NotBlank
	private String surname;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@OneToOne
	@JoinColumn(name = "RESTAURANT_ID")
	private Restaurant restaurant;

}
