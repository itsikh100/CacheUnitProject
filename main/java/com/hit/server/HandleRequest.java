package com.hit.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

public class HandleRequest<T> implements Runnable{
	Socket socket;
	CacheUnitController<T> controller;

	public HandleRequest(Socket s, CacheUnitController<T> controller){
		socket = s;
		this.controller = controller;
	}

	@SuppressWarnings("rawtypes")
	public void run(){
		ObjectOutputStream out = null;
		ObjectInputStream in =null;
		Request<DataModel<T>[]> request =null;

		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
				
			String req = (String)in.readObject();
			System.out.println(req);
			
			Type ref = new TypeToken<Request<DataModel<T>[]>>(){}.getType();
			request = new Gson().fromJson(req, ref); 
			String TypeOfAction = request.getHeaders().get("action");
			
			if (TypeOfAction !=null) {
				switch(TypeOfAction){

				case "GET": {
					DataModel[] response = controller.get(request.getBody());
					System.out.println(response);
					StringBuffer sb = new StringBuffer(100);
					String str = null;
					if(response[0] != null) {
						for(DataModel s: response) {
							str = sb.append(s.toString()).append(", ").toString();
						}
						out.writeObject(str);
					}
					else
						out.writeObject("Not a valid ids in cache");
					break;
				}
				case "UPDATE": {
					Boolean response = controller.update(request.getBody());
					out.writeObject(response.toString());
					break;
				}
				case "DELETE": {
					Boolean response = controller.delete(request.getBody());
					out.writeObject(response.toString());
					break;
				}
				
				case "STATISTICS" :{
					String response = controller.getStatistics();
					out.writeObject(response);
					break;
				}

			}
		}

	} catch (IOException | ClassNotFoundException e) {
		e.printStackTrace();
	}


}

}
