// File Name GreetingClient.java
import java.net.*;
import java.io.*;

public class SendDictSocket {

   
      String serverName; 
      int port;
      SendDictSocket(String serverName){
    	  this.port = 6067;
    	  this.serverName = serverName;
      }
      public void send(String message){
	      try {
	         System.out.println("Connecting to " + serverName + " on port " + port);
	         Socket client = new Socket(serverName, port);     
	         System.out.println("Just connected to " + client.getRemoteSocketAddress());
	         OutputStream outToServer = client.getOutputStream();
	         DataOutputStream out = new DataOutputStream(outToServer);
	         
	         out.writeUTF(message);
	         InputStream inFromServer = client.getInputStream();
	         DataInputStream in = new DataInputStream(inFromServer);
	         
	         System.out.println("Server says " + in.readUTF());
	         in.close();
	         out.close();
	         client.close();
	      }catch(IOException e) {
	         e.printStackTrace();
	      }
      }
   
}
