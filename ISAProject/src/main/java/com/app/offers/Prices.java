package com.app.offers;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "prices")
public class Prices {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRICES_ID")
	private Long id;
	
	@NotNull
	private Long itemId;
	
	@NotNull
	private BigDecimal price;
	
}
