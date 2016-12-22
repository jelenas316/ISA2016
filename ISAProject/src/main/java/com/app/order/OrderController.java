package com.app.order;

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
@RequestMapping(path="/orders")
public class OrderController {
	
	private final OrderService orderService;
	
	@Autowired
	public OrderController(final OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Order> findAll(){
		return orderService.findAll();
	}
	
	@GetMapping(params="id")
	@ResponseStatus(HttpStatus.OK)
	public Order findOne(@PathParam("id") Long id){
		return orderService.findOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Order save(@Valid @RequestBody Order order){
		return orderService.save(order);
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("id") Long id){
		Optional.ofNullable(orderService.findOne(id)).orElseThrow(() -> new ResourceNotFoundException());
		orderService.delete(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Order put(@Valid @RequestBody Order order){
		Optional.ofNullable(orderService.findOne(order.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return orderService.save(order);
	}
	
}
