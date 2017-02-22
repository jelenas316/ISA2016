package com.app.shift;

import java.util.Date;
import java.util.List;

import com.app.restauranttable.RestaurantTable;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class ShiftDTO {
	
	private String worker;
	
	private int beginOfShift;

	private int endOfShift;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date startDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date endDate;
	
	private List<RestaurantTable> reon;	
}
