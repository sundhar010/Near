package com.example.chaitanya.test;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SendMyName  extends AsyncTask<Void, Void, Void> {

    String IP_of_tracker;
    String name;
    int port_to_send_name;
    SendMyName(String name ,String IP_of_tracker, int port_to_send_name)  {
        this.IP_of_tracker =  IP_of_tracker;
        this.port_to_send_name=port_to_send_name;
        this.name=name;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            System.out.println("Connecting to " + IP_of_tracker + " on port " + port_to_send_name);
            Socket client = new Socket(IP_of_tracker, port_to_send_name);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF("Hello from " + name + client.getLocalSocketAddress());
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println("Tracker says " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
