package com.app.reon;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "reon")
public class Reon {
	
	private int id;
	
	private ArrayList<Table> tables;

}
