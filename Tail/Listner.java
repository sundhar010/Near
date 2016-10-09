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



public class Listner extends  Thread {
	private ServerSocket serverSocket;
	DictNameIp  Dict; 

	public Listner(int port,DictNameIp Dict) throws IOException {
		serverSocket = new ServerSocket(port);
		this.Dict =Dict;
	}
	public  boolean UpdateDictSendAll(String key,String val){
		System.out.println("***");
		Dict.Dict.put(key,val);
		Set<String> set = Dict.Dict.keySet();
		System.out.println( set);
		
		for(String keyy: set)
			System.out.println(keyy + " - " + Dict.Dict.get(keyy));
		System.out.println();
		
		String message = "";
		
		for(String keyy: set){
			message += (String)(keyy + " - " + Dict.Dict.get(keyy)+ ";");
		}
		for(String keyy: set){
			SendDictSocket sennd = new SendDictSocket((String)Dict.Dict.get(keyy));
			System.out.println((String)Dict.Dict.get(keyy));
			sennd.send(message);
		}
		return true;	
		
	}

	public void run() {
		while(true) {
			try {
				
				
				System.out.println("Waiting for client on port " + 
						serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());
				String NameIp = in.readUTF();
				System.out.println(NameIp);
				String[] parts = NameIp.split(":");
				parts[1]=parts[1].replace("/", "");
				System.out.println(parts[1] + ":"+ parts[2]);
				try {
					Thread.sleep(300);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				UpdateDictSendAll(parts[0],parts[1]);
				/*	DataOutputStream out = new DataOutputStream(server.getOutputStream());
					out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
					+ "\nGoodbye!");*/
				server.close();

			}catch(IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}	
