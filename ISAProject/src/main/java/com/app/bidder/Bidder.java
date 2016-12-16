package com.app.bidder;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.app.offers.Offer;

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

	@NotBlank
	private String name;
	
	//@OneToMany
	//private List<Offer> offers;

}
