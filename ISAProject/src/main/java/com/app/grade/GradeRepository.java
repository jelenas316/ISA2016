package com.app.grade;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends CrudRepository<Grade, Long>{

	public Iterable<Grade> findByGuestEmail(String email);
	public List<Grade> findByRestaurantId(Long id);
	public List<Grade> findByWaiterEmail(String email);
	public List<Grade> findByFoodId(Long id);
}
