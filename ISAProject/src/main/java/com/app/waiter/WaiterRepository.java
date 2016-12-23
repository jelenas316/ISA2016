package com.app.waiter;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaiterRepository extends CrudRepository<Waiter, String> {

}
