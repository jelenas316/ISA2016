package com.app.grade;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends CrudRepository<Grade, Long>{

	public Iterable<Grade> findByGuestEmail(String email);
	public Iterable<Grade> findByRestaurantId(Long id);
	
}
