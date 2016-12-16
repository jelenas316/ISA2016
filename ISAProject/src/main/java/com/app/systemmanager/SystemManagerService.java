package com.app.systemmanager;

public interface SystemManagerService {
	
	Iterable<SystemManager> findAll();
	SystemManager findOne(String email);
	SystemManager save(SystemManager systemManager);
	void delete(String email);
}
