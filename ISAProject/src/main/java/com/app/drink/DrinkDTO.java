package com.app.drink;

import java.math.BigDecimal;

import com.sun.istack.Nullable;

import lombok.Data;
@Data
public class DrinkDTO {
	@Nullable
	private Long id;
	
	private String name;

	private String description;
	
	private BigDecimal price;
	
	private Integer resId;
}
