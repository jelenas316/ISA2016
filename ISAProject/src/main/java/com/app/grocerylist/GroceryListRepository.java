
package com.app.grocerylist;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.restaurant.Restaurant;
@Repository
public interface GroceryListRepository extends CrudRepository<GroceryList,Long>{
	List<GroceryList> findByRestaurant(Restaurant id);
}
