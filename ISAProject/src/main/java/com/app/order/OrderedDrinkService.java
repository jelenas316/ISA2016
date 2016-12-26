package com.app.order;

public interface OrderedDrinkService {
	
	Iterable<OrderedDrink> findAll();
	OrderedDrink findOne(Long id);
	OrderedDrink save(OrderedDrink orderedDrink);
	void delete(Long id);

}
