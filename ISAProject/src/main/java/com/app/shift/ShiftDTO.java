package com.app.shift;

import java.util.Date;
import java.util.List;

import com.app.restauranttable.RestaurantTable;

import lombok.Data;
@Data
public class ShiftDTO {
	
	private Long id;
	
	private String worker;
	
	private int beginOfShift;

	private int endOfShift;
	
	private Date startDate;
	
	private Date endDate;
	
	private List<RestaurantTable> reon;	
}
