package com.app.systemmanager;

import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping(path="/systemmanagers")
public class SystemManagerController {
	
	private final SystemManagerService systemManagerService;
	
	@Autowired
	public SystemManagerController(final SystemManagerService systemManagerService) {
		this.systemManagerService=systemManagerService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<SystemManager> findAll(){
		return systemManagerService.findAll();
	}
	
	@GetMapping(params="email")
	@ResponseStatus(HttpStatus.OK)
	public SystemManager findOne(@PathParam("email") String email){
		return systemManagerService.findOne(email);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SystemManager save(@Valid @RequestBody SystemManager systemManager){
		
		if(systemManagerService.findOne(systemManager.getEmail())!= null){
			throw new DataIntegrityViolationException("Duplicate entry for key "+ systemManager.getEmail());
		}
		return systemManagerService.save(systemManager);
	}
	
	@DeleteMapping(params="email")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("email") String email){
		Optional.ofNullable(systemManagerService.findOne(email)).orElseThrow(() -> new ResourceNotFoundException());
		systemManagerService.delete(email);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public SystemManager put(@Valid @RequestBody SystemManager systemManager){
		Optional.ofNullable(systemManagerService.findOne(systemManager.getEmail())).orElseThrow(() -> new ResourceNotFoundException());
		return systemManagerService.save(systemManager);
	}

}
