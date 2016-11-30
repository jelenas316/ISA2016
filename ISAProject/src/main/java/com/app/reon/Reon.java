package com.app.reon;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
//@Entity
//@Table(name = "reon")
public class Reon {
	
//	@Id
	private int id;
	
	private ArrayList<Table> tables;

}
