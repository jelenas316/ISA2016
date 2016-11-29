package com.app.user;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="user", catalog="isa2016")
public class User {

	@Id
	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String name;

	@NotNull
	private String surname;

	@NotNull
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
}
