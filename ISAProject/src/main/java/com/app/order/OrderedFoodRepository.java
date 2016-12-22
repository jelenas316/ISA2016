package com.app.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedFoodRepository extends CrudRepository<OrderedFood, Long>{

}
