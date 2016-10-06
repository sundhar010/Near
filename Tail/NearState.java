																																																																																																																																																																																																																																																																			import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;


public class NearState extends  Thread {
	DictNameIp  Dict; 
	public NearState(DictNameIp Dict) {
		this.Dict =Dict;
	}
	public void run(){
		while(true){
			String serverName;
			int port;
			Set<String> set = Dict.Dict.keySet();
			System.out.println( set);
			if(Dict.Dict.size()>0){
				for(String keyy: set){
					serverName =(String)Dict.Dict.get(keyy);
					port = 6068;
					try {
				         System.out.println("Connecting to " + serverName + " on port " + port);
				         Socket client = new Socket(serverName, port);
				         
				         System.out.println("Just connected to " + client.getRemoteSocketAddress());
				         java.io.OutputStream outToServer = client.getOutputStream();
				         DataOutputStream out = new DataOutputStream(outToServer);
				         
				         out.writeUTF("Alive??");
				         java.io.InputStream inFromServer = client.getInputStream();
				         DataInputStream in = new DataInputStream(inFromServer);
				         
				         System.out.println("Server says " + in.readUTF());
				         client.close();
				      }catch(IOException e) {
				    	  DelDictSendAll(keyy);
				         e.printStackTrace();
				    }
				}
			}
		}
	}
	public void DelDictSendAll(String key){
		Set<String> set = Dict.Dict.keySet();
		System.out.println( set);
		for(String keyy: set)
			System.out.println(keyy + " - " + Dict.Dict.get(keyy));
		System.out.println();
		Dict.Dict.remove(key);
		String message = null;
		for(String keyy: set){
			message = (String)(keyy + " - " + Dict.Dict.get(keyy)+ ";");
		}
		for(String keyy: set){
			SendDictSocket sennd = new SendDictSocket((String)Dict.Dict.get(keyy));
			sennd.send(message);
		}
		System.out.println(message);
		
	}
}
