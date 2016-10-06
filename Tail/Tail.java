import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;



public class Tail {	
        public static void main(String [] args) {
        	DictNameIp Dict = new DictNameIp();
                try{
                        int port = Integer.parseInt(args[0]);
                        Thread t = new Listner(port,Dict);
                        t.start();
                }catch(IOException e) {
                        e.printStackTrace();
                }
        }
}
