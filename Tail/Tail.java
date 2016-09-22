import java.net.*;
import java.io.*;

public class Tail {
	public static void main(String [] args) {
		try{
			int port = Integer.parseInt(args[0]);
			Thread t = new Listner(port);
			t.start();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
