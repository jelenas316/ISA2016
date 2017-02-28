package com.app.bidder;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.app.offers.Offer;
import com.app.restaurant.Restaurant;
import com.app.user.UserRole;

import lombok.Data;
@Data
@Entity
@Table(name="bidder")
public class Bidder {

	@Id
	@NotBlank
	private String email;

	@NotBlank
	private String password;
	
	//0-initial not changed password, 1- bidder changed password
	private int passwordStatus;

	@NotBlank
	private String name;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@ManyToOne
	@JoinColumn(name = "RESTAURANT")
	private Restaurant restaurant;

}
