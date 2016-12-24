package com.app.shift;

import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/shifts")
public class ShiftController {
	
	private final ShiftService shiftService;
	
	@Autowired
	public ShiftController(final ShiftService shiftService) {
		this.shiftService = shiftService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Shift> findAll(){
		return shiftService.findAll();
	}
	
	@GetMapping(params="id")
	@ResponseStatus(HttpStatus.OK)
	public Shift findOne(@PathParam("id") Long id){
		return shiftService.findOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Shift save(@Valid @RequestBody Shift shift){
		return shiftService.save(shift);
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("id") Long id){
		Optional.ofNullable(shiftService.findOne(id)).orElseThrow(() -> new ResourceNotFoundException());
		shiftService.delete(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Shift put(@Valid @RequestBody Shift shift){
		Optional.ofNullable(shiftService.findOne(shift.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return shiftService.save(shift);
	}

}
