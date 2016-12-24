package com.app.shift;

public interface ShiftService {

	Iterable<Shift> findAll();
	Shift findOne(Long id);
	Shift save(Shift shift);
	void delete(Long id);
}
