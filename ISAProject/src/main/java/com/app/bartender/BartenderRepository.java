package com.app.bartender;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BartenderRepository extends CrudRepository<Bartender, String>{

}
