package com.app.profileActivation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;


@Data
@Table(name = "profile")
@Entity
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PROFILE_ID")
	private Long id;
	
	@NotBlank
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String name;

	@NotBlank
	private String surname;
	
}
