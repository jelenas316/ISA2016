package com.app.table;

import javax.validation.constraints.NotNull;

import com.app.configuration.Configuration;

import lombok.Data;

@Data
//@Entity
//@javax.persistence.Table(name = "table")
public class Table {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "TABLE_ID")
	private Long id;
	
	@NotNull
	private Integer number;
	
	private Configuration configuration;
	
}
