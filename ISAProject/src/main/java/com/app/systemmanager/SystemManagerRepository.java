package com.app.systemmanager;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemManagerRepository extends CrudRepository<SystemManager, String>{

}
