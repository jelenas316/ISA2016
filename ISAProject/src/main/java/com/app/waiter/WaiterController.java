package com.app.waiter;

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
@RequestMapping(path = "/waiters")
public class WaiterController {

	private final WaiterService waiterService;
	
	@Autowired
	public WaiterController(final WaiterService waiterService){
		this.waiterService = waiterService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Waiter> findAll(){
		return waiterService.findAll();
	}
	
	@GetMapping(params = "email")
	@ResponseStatus(HttpStatus.OK)
	public Waiter findOne(@PathParam("email") String email){
		return waiterService.findOne(email);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Waiter save(@Valid @RequestBody Waiter waiter){
		return waiterService.save(waiter);
	}
	
	@DeleteMapping(params = "email")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("email") String email){
		Optional.ofNullable(waiterService.findOne(email)).orElseThrow(() -> new ResourceNotFoundException());
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Waiter put(@Valid @RequestBody Waiter waiter){
		Optional.ofNullable(waiterService.findOne(waiter.getEmail())).orElseThrow(() -> new ResourceNotFoundException());
		return waiterService.save(waiter);
	}
}
