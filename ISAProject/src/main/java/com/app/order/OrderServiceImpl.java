package com.app.order;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{

	private final OrderRepository orderRepo;

	@Autowired
	public OrderServiceImpl(final OrderRepository orderRepo) {
		this.orderRepo=orderRepo;
	}
	
	@Override
	public Iterable<Order> findAll() {
		return orderRepo.findAll();
	}

	@Override
	public Order findOne(Long id) {
		return orderRepo.findOne(id);
	}

	@Override
	public Order save(Order order) {
		return orderRepo.save(order);
	}

	@Override
	public void delete(Long id) {
		orderRepo.delete(id);
	}

}
