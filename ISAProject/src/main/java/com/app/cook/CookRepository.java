package com.app.cook;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookRepository extends CrudRepository<Cook, String> {

}
