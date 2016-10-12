package com.project.near.app;


import android.app.Activity;
import android.content.Context;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class RcvChatMessage extends Thread {
    DatagramSocket serverSocket;

    int port;
    Context context;

    RcvChatMessage(Context context){
        port = 6069;
        this.context = context;
    }
    public void run() {
        try {
            System.out.println("Rcvr thread running");
            serverSocket = new DatagramSocket(port);
        while (true) {
            byte[] receiveData;
            receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Rcvr socket fine");
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            System.out.println("RECEIVED: " + sentence);
            String IP =receivePacket.getAddress().getHostAddress().toString();
            System.out.println("RECEIVED: from "+ IP);
            String puts ="L_"+sentence;
         //   ((ChatDict) ((Activity)context).getApplication()).setChat(IP,puts);
        }
    }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
