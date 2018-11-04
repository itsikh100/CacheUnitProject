package com.hit.dao;

import com.hit.dm.DataModel;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DaoFileImpl<T>  implements IDao<Long,DataModel<T>> {

	private final String filePath;
	private Map<Long, DataModel<T>> daoMap;

	public DaoFileImpl(String filePath){          // constructor     
		this.filePath = filePath;
		this.daoMap = new HashMap(500);  // 500 is the default Size of HashMap.
	}

	public DaoFileImpl(String filePath, int capacity){           // constructor
		this.filePath = filePath;
		this.daoMap = new HashMap(capacity);
	}

	@Override
	public void save(DataModel<T> t){
		try {
			if (t != null) {
				this.daoMap.put(t.getDataModelId(), t);
				writeMapToFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(DataModel<T> t) throws IllegalArgumentException {        //Exception in case the given entity is null.
		if (t == null) {
			throw new IllegalArgumentException();
		}

		try {
			this.daoMap.remove(t.getDataModelId(), t);
			writeMapToFile();
		} catch (IOException  e) {
			e.printStackTrace();
		}
	}

	@Override
	public DataModel<T> find(Long id) throws IllegalArgumentException {
		DataModel<T> retValue = null;

		if (id == null) {
			throw new IllegalArgumentException("");
		}

		try {
			readMapFromFile();
			retValue = this.daoMap.get(id);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return retValue;
	}

	private void writeMapToFile() throws IOException {
		try (ObjectOutputStream outputStream = 
				new ObjectOutputStream(new FileOutputStream(this.filePath))) {

			outputStream.writeObject(this.daoMap);
		}
	}


	@SuppressWarnings("unchecked")
	private void readMapFromFile() throws IOException,ClassNotFoundException {
		try (ObjectInputStream inputStream = 
				new ObjectInputStream(new FileInputStream(this.filePath))) {
			this.daoMap = (HashMap<Long, DataModel<T>>)inputStream.readObject();
		}
	}
}