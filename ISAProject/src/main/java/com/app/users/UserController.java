package com.app.users;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	
	@Autowired
	public UserController(final UserService userService){
		this.userService=userService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<User> findAll(){
		System.out.println("1");
		return userService.findAll();
	}
	
	@GetMapping(path="/{email}")
	@ResponseStatus(HttpStatus.OK)
	public User findOne(@PathVariable String email){
		return userService.findOne(email);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes="application/json")
	public void save(@Valid @RequestBody User user){
		userService.save(user);
	}
	
	@DeleteMapping(path="/{email}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String email){
		userService.delete(email);
	}
	
	@PutMapping(path="/{email}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable String email, @Valid @RequestBody User user){
//		Optional.ofNullable(userService.findOne(email)).orElseThrow(() -> new ResourceNotFoundException("resourceNotFound"));
		userService.save(user);
	}
	
	
	
	
}
