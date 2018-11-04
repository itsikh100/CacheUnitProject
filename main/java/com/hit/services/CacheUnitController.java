package com.hit.services;

import com.hit.dm.DataModel;

public class CacheUnitController<T> {
	private CacheUnitService<T> service;

	public CacheUnitController(){
		service = new CacheUnitService<T>();
	}

	public boolean update(DataModel<T>[] dataModels){
		return this.service.update(dataModels);
	}

	public boolean delete(DataModel<T>[] dataModels){
		return this.service.delete(dataModels);
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels){
		return this.service.get(dataModels);
	}
	
	public String getStatistics() {
		return this.service.getStatistics();
	}
}
