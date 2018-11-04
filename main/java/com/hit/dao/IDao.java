package com.hit.dao;

public interface IDao<ID extends java.io.Serializable,T> {

	void save(T entity);       //Save a given entity.
	void delete(T entity);     //Deletes a given entity.
	T find(ID id);       //Retrieves an entity by its id.
}