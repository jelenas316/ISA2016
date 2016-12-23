package com.app.guest;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.app.user.UserRole;

import lombok.Data;

@Data
@Entity
@Table(name="guest")
public class Guest{

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

	@OneToMany
	private List<Guest> friends;

	@OneToMany
	@Column(name="FRIEND_REQUESTS")
	private List<Guest> friendRequests;
	
	
}
