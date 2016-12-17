package com.app.grade;

public interface GradeService {

	Iterable<Grade> findAll();
	Grade findOne(Long id);
	Grade save(Grade grade);
	void delete(Long id);
	Iterable<Grade> findByGuest(String email);
	Iterable<Grade> findByRestaurant(Long id);
	
}
