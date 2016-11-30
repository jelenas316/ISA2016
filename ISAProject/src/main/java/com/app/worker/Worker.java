package com.app.worker;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
//@Entity
//@Table(name = "worker")
public class Worker {
	
//	@Id
	private int id;
	
	private String name;
	private String surname;
	private String dateOfBirth;
	private int dressSize;
	private double shoesSize; 
}
