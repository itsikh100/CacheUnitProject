package com.hit.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class CLI implements Runnable{
	private Scanner scan;
	private PrintWriter writer;
	public  String Answer = "wait";
	private  PropertyChangeSupport change;
	
	public CLI(InputStream in, OutputStream out){
		scan = new Scanner(in);
		writer = new PrintWriter(out);
		change = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl){
		change.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl){
		change.removePropertyChangeListener(pcl);
	}

	public void write(String string){
		System.out.println(string);
	}

	@Override
	public void run(){
		try {			
			while (!(Answer.equals("stop"))) {
				this.write("Please enter Command: ");
				Answer = scan.nextLine();
				Answer.toLowerCase();

				if (Answer.equals("start") || Answer.equals("stop")) {
					change.firePropertyChange("Answer", null, Answer);
					
				}

				else
					this.write("Not a valid Command, please try again: (start/stop)");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}

