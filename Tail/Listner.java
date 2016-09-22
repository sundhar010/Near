import java.net.*;
import java.io.*;



public class Listner extends Thread {
	private ServerSocket serverSocket;

	public Listner(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public void run() {
		while(true) {
			try {
				System.out.println("Waiting for client on port " + 
						serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());
				System.out.println(in.readUTF());
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