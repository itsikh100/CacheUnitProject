package com.hit.services;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitService<T> {
	private CacheUnit<String> cacheUnit;
	private IAlgoCache<Long,DataModel<String>> lru;
	private IDao<Long, DataModel<String>> dao;
	private int requestCounter;
	private int DataModelsCounter;
	private int swapCounter;


	public CacheUnitService() {
		dao = new DaoFileImpl<String>("main\\resources\\datasources.txt‬");
		requestCounter = 0;
		DataModelsCounter = 0;
		lru = new LRUAlgoCacheImpl<Long,DataModel<String>>(5);
		this.cacheUnit = new CacheUnit<String>(lru);	
	}

	public boolean update(DataModel<T>[] dataModels){
		requestCounter++;
		try {

			DataModel<T>[] Resault = 
					(DataModel<T>[]) cacheUnit.putDataModels((DataModel<String>[]) dataModels);
			if (Resault[0] != null)
				for(int i =0; i <Resault.length; i++) {
					dao.save((DataModel<String>) Resault[i]);
					DataModelsCounter++;
				}
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean delete(DataModel<T>[] dataModels){
		requestCounter++;
		try {
			Long [] ids = new Long[dataModels.length];
			for(int i =0; i < dataModels.length; i++) {
				ids[i] = dataModels[i].getDataModelId();
				dao.delete((DataModel<String>) dataModels[i]);
				DataModelsCounter++;
			}
			cacheUnit.removeDataModels(ids);
		}
		catch (Exception e) {
			return false;
		}
		return true;

	}

	public DataModel<T>[] get(DataModel<T>[] dataModels){
		requestCounter++;
		try{
			Long [] ids = new Long[dataModels.length];
			for(int i =0; i < dataModels.length; i++)  {
				ids[i] = dataModels[i].getDataModelId();
				DataModelsCounter++;
			}
			DataModel<T>[] Resault = (DataModel<T>[]) cacheUnit.getDataModels(ids);
			for (int i =0; i < dataModels.length; i++) {
				if (Resault[i] == null) {
					Resault[i] = (DataModel<T>) dao.find(dataModels[i].getDataModelId());	
					swapCounter++;
				}
			}
			return Resault;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public String getStatistics() {
		return "<html>" +"Capacity: 5" + "<br>" + "Algorithm: LRU" + "<br>"
				+"Total number ofRequest: " + requestCounter + "<br>" 
				+ "Total number of DataModels: " + DataModelsCounter  + "<br>"
				+ "Total number of DataModels swaps: " + swapCounter + "</html>" ;
	}

}
