package com.app.waiter;


public interface WaiterService {
	
	Iterable<Waiter> findAll();
	Waiter findOne(String email);
	Waiter save(Waiter waiter);
	void delete(String email);

}
