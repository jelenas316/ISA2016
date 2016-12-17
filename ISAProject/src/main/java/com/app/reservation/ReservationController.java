package com.app.reservation;

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
@RequestMapping(path="/reservations")
public class ReservationController {
	
	private final ReservationService reservationService;
	
	@Autowired
	public ReservationController(final ReservationService reservationService) {
		this.reservationService=reservationService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Reservation> findAll(){
		return reservationService.findAll();
	}
	
	@GetMapping(params="id")
	@ResponseStatus(HttpStatus.OK)
	public Reservation findOne(@PathParam("id") Long id){
		return reservationService.findOne(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Reservation save(@Valid @RequestBody Reservation reservation){
		return reservationService.save(reservation);
	}
	
	@DeleteMapping(params="id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("id") Long id){
		Optional.ofNullable(reservationService.findOne(id)).orElseThrow(() -> new ResourceNotFoundException());
		reservationService.delete(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public Reservation put(@Valid @RequestBody Reservation reservation){
		Optional.ofNullable(reservationService.findOne(reservation.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return reservationService.save(reservation);
	}
}
