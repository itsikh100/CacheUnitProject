package com.hit.server;
import java.io.Serializable;
import java.util.Map;

public class Request<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T body;
	private Map<String,String> headers;

	public Request(Map<String,String> headers, T body){
		this.body = body;
		this.headers = headers;
	}

	public Map<String,String> getHeaders(){
		return this.headers;
	}

	public void setHeaders(Map<String,String> headers){
		this.headers = headers;
	}

	public T getBody(){
		return this.body;
	}

	public void setBody(T body){
		this.body = body;
	}

	@Override
	public String toString(){
		return "headers:" + this.headers.toString() + "\n\nBody:\n" + this.body.toString();
	}
}
