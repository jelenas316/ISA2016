package com.app.systemmanager;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@Transactional
public class SystemManagerServiceImpl implements SystemManagerService{


	private final SystemManagerRepository systemManagerRepo;
	
	@Autowired
	public SystemManagerServiceImpl(final SystemManagerRepository systemManagerRepo) {
		this.systemManagerRepo=systemManagerRepo;
	}
	
	@Override
	public Iterable<SystemManager> findAll() {
		return systemManagerRepo.findAll();
	}

	@Override
	public SystemManager findOne(String email) {
		return systemManagerRepo.findOne(email);
	}

	@Override
	public SystemManager save(SystemManager systemManager) {
		return systemManagerRepo.save(systemManager);
	}

	@Override
	public void delete(String email) {
		systemManagerRepo.delete(email);
	}

}
