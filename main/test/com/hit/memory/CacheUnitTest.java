package com.hit.memory;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import com.hit.dao.*;
import com.hit.dm.*;
import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;

class CacheUnitTest {
	IAlgoCache<Long,DataModel<String>> lru = new LRUAlgoCacheImpl<Long,DataModel<String>>(3);  
	IDao<Long,DataModel<String>> dao = new DaoFileImpl<String>("main\\resources\\datasources.txt‬");
	CacheUnit<String> cacheUnit = new CacheUnit<String>(lru);

	DataModel<String> data1 = new DataModel<String>(Long.valueOf(1), "1");
	DataModel<String> data2 = new DataModel<String>(Long.valueOf(2), "2");		//Initialize DataModels
	DataModel<String> data3 = new DataModel<String>(Long.valueOf(3), "3");		//
	DataModel<String> data4 = new DataModel<String>(Long.valueOf(4), "4");

	Long[] ids = {Long.valueOf(1) ,Long.valueOf(2), Long.valueOf(3), Long.valueOf(4)};


	@Test
	public void daoFileImpllTest() {	 
		dao.save(data1);
		dao.save(data2);		//Initialize file with DataModels
		dao.save(data3);
		dao.save(data4);

		DataModel<String> checkInFile;
		checkInFile = dao.find(data1.getDataModelId());
		Assert.assertEquals(checkInFile, data1);
		checkInFile = dao.find(data2.getDataModelId());
		Assert.assertEquals(checkInFile, data2);
		checkInFile = dao.find(data3.getDataModelId());		// Check that all the DataModel can be found
		Assert.assertEquals(checkInFile, data3);			// on the file
		checkInFile = dao.find(data4.getDataModelId());
		Assert.assertEquals(checkInFile, data4);
		checkInFile = dao.find(Long.valueOf(5));//check if FIND Method didn't find nothing
		Assert.assertEquals(checkInFile, null);

		data1 = new DataModel<String>(Long.valueOf(1), "10");		//Change the entity
		dao.save(data1);

		checkInFile = dao.find(data1.getDataModelId());				//Check the content on file 
		Assert.assertEquals(checkInFile.getContent(), "10");		//after update the DataModel

	}
	@Test
	public void CacheUnitTests() {
		DataModel[] DataModelsArray = {data1, data2,data3,data4};
		DataModel[] ReturnValues = cacheUnit.putDataModels(DataModelsArray);  // The last data we used was the first 
		Assert.assertEquals(ReturnValues[0], data1);						  // to came out from cache "LRU" -> 1

		
		cacheUnit.removeDataModels(ids);		//Now remove all DataModels

		ReturnValues = cacheUnit.getDataModels(ids);		//After remove DataModels
		Assert.assertEquals(ReturnValues[0], null);			//getDataModel return null
	}




}
