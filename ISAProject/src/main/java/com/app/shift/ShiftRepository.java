package com.app.shift;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends CrudRepository<Shift, Long>{

}
