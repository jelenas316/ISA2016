package com.app.grade;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GradeServiceImpl implements GradeService{

	private final GradeRepository gradeRepo;
	
	@Autowired
	public GradeServiceImpl(final GradeRepository gradeRepo) {
		this.gradeRepo=gradeRepo;
	}
	
	@Override
	public Iterable<Grade> findAll() {
		return gradeRepo.findAll();
	}

	@Override
	public Grade findOne(Long id) {
		return gradeRepo.findOne(id);
	}

	@Override
	public Grade save(Grade grade) {
		return gradeRepo.save(grade);
	}

	@Override
	public void delete(Long id) {
		gradeRepo.delete(id);
	}
	
	public Iterable<Grade> findByGuest(String email){
		return gradeRepo.findByGuestEmail(email);
	}

	@Override
	public Iterable<Grade> findByRestaurant(Long id) {
		return gradeRepo.findByRestaurantId(id);
	}

}
