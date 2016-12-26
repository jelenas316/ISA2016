package com.app.order;

public interface OrderedFoodService {

	Iterable<OrderedFood> findAll();
	OrderedFood findOne(Long id);
	OrderedFood save(OrderedFood orderedFood);
	void delete(Long id);

}
