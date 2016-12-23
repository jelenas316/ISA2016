package com.app.bartender;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.app.user.UserRole;

import lombok.Data;

@Data
@Entity
@Table(name = "bartender")
public class Bartender {

	@Id
	@NotBlank
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
	
}
