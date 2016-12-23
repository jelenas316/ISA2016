package com.app.bartender;

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
@RequestMapping(path = "/bartenders")
public class BartenderController {
	
	private final BartenderService bartenderService;
	
	@Autowired
	public BartenderController(final BartenderService bartenderService){
		this.bartenderService = bartenderService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Bartender> findAll(){
		return bartenderService.findAll();
	}
	
	@GetMapping(params = "email")
	@ResponseStatus(HttpStatus.OK)
	public Bartender findOne(@PathParam("email") String email){
		return bartenderService.findOne(email);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Bartender save(@Valid @RequestBody Bartender bartender){
		return bartenderService.save(bartender);
	}
	
	@DeleteMapping(params = "email")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("email") String email){
		Optional.ofNullable(bartenderService.findOne(email)).orElseThrow(() -> new ResourceNotFoundException());
		bartenderService.delete(email);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Bartender put(@Valid @RequestBody Bartender bartender) {
		Optional.ofNullable(bartenderService.findOne(bartender.getEmail())).orElseThrow(() -> new ResourceNotFoundException());
		return bartenderService.save(bartender);
	}

}
