package com.app.shift;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.app.reon.Reon;
import com.app.worker.Worker;

import lombok.Data;

@Data
//@Table(name = "shift")
//@Entity
public class Shift {

//	@Id	
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "SHIFT_ID")
	private Long id;
	
	@NotNull
	private Date beginOfShift;

	@NotNull
	private Date endOfShift;
	
	@NotNull
	private Worker worker;
	
	@NotNull
	private Reon reon;
	
}
