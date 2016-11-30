package com.app.worker;

import java.math.BigDecimal;

import lombok.Data;

@Data
//@Entity
//@Table(name = "worker")
public class Worker {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "WORKER_ID")
	private Long id;
	
	private String name;
	private String surname;
	private String dateOfBirth;
	private Integer dressSize;
	private BigDecimal shoesSize; 
}
