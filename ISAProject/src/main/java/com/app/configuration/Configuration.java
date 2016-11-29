package com.app.configuration;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.restaurant.Restaurant;

import lombok.Data;
@Data
@Entity
@Table(name = "configuration")
public class Configuration {

	private long id;

	private Restaurant restaurant;
	
	private Segment segment;
	
	private ArrayList<Table> tables;
}
