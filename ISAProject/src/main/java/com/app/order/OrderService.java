package com.app.order;

public interface OrderService {

	Iterable<Order> findAll();
	Order findOne(Long id);
	Order save(Order order);
	void delete(Long id);
	
}
