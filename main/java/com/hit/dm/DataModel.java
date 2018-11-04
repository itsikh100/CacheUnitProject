package com.hit.dm;

public class DataModel<T> implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private T content;

	public DataModel(Long id, T content) {         // Constructor
		this.id = id;
		this.content = content;
	}

	@Override 
	public int hashCode() {
		return this.id.intValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (this.hashCode() == obj.hashCode())
		return true;
		else return false;
	}

	@Override
	public String toString() {
		return "ID: " + id + "\nContent: " + content;
	}

	public Long getDataModelId() {    
		return this.id;
	}

	public void setDataModelId(Long id) {    
		this.id = id;
	}

	public T getContent() {    
		return this.content;
	}

	public void setContent(T content) {    
		this.content = content;
	}

}
