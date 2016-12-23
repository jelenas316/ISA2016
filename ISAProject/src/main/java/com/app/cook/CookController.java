package com.app.cook;

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
@RequestMapping(path = "/cooks")
public class CookController {
	
	private final CookService cookService;
	
	@Autowired
	public CookController(final CookService cookService) {
		this.cookService = cookService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Cook> findAll() {
		return cookService.findAll();
	}
	
	@GetMapping(params = "email")
	@ResponseStatus(HttpStatus.OK)
	public Cook findOne(@PathParam("email") String email){
		return cookService.findOne(email);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cook save(@Valid @RequestBody Cook cook){
		return cookService.save(cook);
	}
	
	@DeleteMapping(params = "email")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("email") Cook cook){
		Optional.ofNullable(cookService.findOne(cook.getEmail())).orElseThrow(() -> new ResourceNotFoundException());
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Cook put(@Valid @RequestBody Cook cook){
		Optional.ofNullable(cookService.findOne(cook.getEmail())).orElseThrow(() -> new ResourceNotFoundException());
		return cookService.save(cook);
	}

}
