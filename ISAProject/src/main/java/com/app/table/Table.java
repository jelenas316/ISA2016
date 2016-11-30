package com.app.table;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.app.configuration.Configuration;
import com.app.restaurant.Restaurant;

import lombok.Data;

@Data
//@Entity
public class Table {
	
//	@Id
	private int id;
	
	private int number;
	
	private Configuration configuration;
	
}
