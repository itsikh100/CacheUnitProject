package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.dm.DataModel;

public class CacheUnit<T> {
	private IAlgoCache<Long,DataModel<T>> algo;
	public CacheUnit(IAlgoCache<Long,DataModel<T>> algo) {
		this.algo = algo;
	}

	public DataModel<T>[] getDataModels(Long[] ids) {
		int i = 0;
		@SuppressWarnings("unchecked")
		DataModel<T>[] Resault = new DataModel[ids.length];
																			// Return Array of DataModels
		for (Long id: ids) {												// if DataModel is null -> Resault[i] = null
			DataModel<T> dataModel = this.algo.getElement(id);    
				Resault[i++] = dataModel;
		}
		return Resault;

	}

	public DataModel<T>[] putDataModels(DataModel<T>[] datamodels) {
		int i=0;
		@SuppressWarnings("unchecked")
		DataModel<T>[] Resault = new DataModel[datamodels.length];
		for(DataModel<T> TempData: datamodels) {											
			DataModel<T> dataModel = 												 	//Return all DataModels that Cache 
					this.algo.putElement(TempData.getDataModelId(), TempData);			// need to take out to\from the Disk
			if (dataModel != null)
				Resault[i++] =  dataModel;
		}
		return Resault; 
	}

	public void removeDataModels(Long[] ids) {
		for(Long id: ids) {				
			this.algo.removeElement(id);
		}
	}

}