package com.app.workschedule;

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

import com.app.waiter.Waiter;
import com.app.waiter.WaiterService;

@RestController
@RequestMapping(path = "/work_schedules")
public class WorkScheduleController {

private final WorkScheduleService wcService;
	
	@Autowired
	public WorkScheduleController(final WorkScheduleService wcService){
		this.wcService = wcService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<WorkSchedule> findAll(){
		return wcService.findAll();
	}
	
	@GetMapping(params = "id")
	@ResponseStatus(HttpStatus.OK)
	public WorkSchedule findOne(@PathParam("id") Long id){
		return wcService.findOne(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public WorkSchedule save(@Valid @RequestBody WorkSchedule wc){
		return wcService.save(wc);
	}
	
	@DeleteMapping(params = "id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathParam("id") Long id){
		Optional.ofNullable(wcService.findOne(id)).orElseThrow(() -> new ResourceNotFoundException());
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public WorkSchedule put(@Valid @RequestBody WorkSchedule wc){
		Optional.ofNullable(wcService.findOne(wc.getId())).orElseThrow(() -> new ResourceNotFoundException());
		return wcService.save(wc);
	}
}
