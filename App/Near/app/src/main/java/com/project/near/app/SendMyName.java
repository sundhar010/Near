package com.project.near.app;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SendMyName  extends AsyncTask<Void, Void, Void> {

    String IP_of_tracker;
    String name;
    Context context;
    Activity act;
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
            System.out.println(name);
            out.writeUTF(name +":"+ client.getLocalSocketAddress());
            client.close();
        } catch (IOException e) {
            System.out.println("Exception");
            e.printStackTrace();
        }
        return null;
    }

}
