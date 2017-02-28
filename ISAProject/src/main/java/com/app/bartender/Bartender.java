package com.app.bartender;

import java.math.BigDecimal;
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

import com.app.restaurant.Restaurant;
import com.app.shift.Shift;
import com.app.user.UserRole;

import lombok.Data;

@Data
@Entity
@Table(name = "bartender")
public class Bartender {

	@Id
	private String email;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String surname;
	
	@NotBlank
	private String dateOfBirth;
	
	@NotNull
	private Integer dressSize;
	
	@NotNull
	private BigDecimal shoesSize; 	
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "RESTAURANT")
	private Restaurant restaurant;
	
	@OneToMany
	private List<Shift> shifts;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	private boolean activated;	
}
