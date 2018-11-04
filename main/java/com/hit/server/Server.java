package com.hit.server;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.hit.services.CacheUnitController;


public class Server implements PropertyChangeListener, Runnable{
	public ServerSocket server;
	public CacheUnitController<String> controller;
	public HandleRequest<String> handleRequest;

	public Server(){
		controller = new CacheUnitController<String>();
	}

	public void propertyChange(java.beans.PropertyChangeEvent evt){
		if (evt.getNewValue().equals("start")) {
			System.out.println("Starting Server...");
			new Thread(this).run();
		}
		
		if (evt.getNewValue().equals("stop")) {
			Thread.currentThread().interrupt();
			System.out.println("Server got " + evt.getNewValue() + " from CLI");
			
		}
	}

	@Override
	public void run(){
		try {
			server = new ServerSocket(12345);
			while(!(Thread.currentThread().isInterrupted())) {
				Socket SomeClient = server.accept();
				handleRequest = 
						new HandleRequest<String>(SomeClient, controller);
				System.out.println("Server Now got request...");
				Thread thread = new Thread(handleRequest);
				thread.start();
			}

			if(server.isClosed() == false) 
				server.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}