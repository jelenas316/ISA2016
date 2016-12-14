package com.app.configuration;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import com.app.restaurant.Restaurant;
import com.app.table.Table;

import lombok.Data;

@Data
//@Entity
//@javax.persistence.Table(name = "configuration")
public class Configuration {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "CONFIGURATION_ID")
	private Long id;
	
	@NotNull
	private Segment segment;
	
	private ArrayList<Table> tables;
}
