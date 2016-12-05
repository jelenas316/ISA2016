package com.app.user;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
//@Entity
//@Table(name="user", catalog="isa2016")
public abstract class User {

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
