package com.project.near.app;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Set;


public class RcvChatMessage extends Thread  {
    DatagramSocket serverSocket;

    int port;
    Context context;


    RcvChatMessage(Context context){
        port = 6069;
       // ((ChatDict) ((Activity)context).getApplication()).setChat("192.168.43.246","L_puts");
        this.context = context;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void run() {
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        byte[] sendData = new byte[1024];
        while(true)
        {
            byte[] receiveData = new byte[1000];
            System.out.println("Rcvr thread running");
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Rcvr socket fine");
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] receved = (receivePacket.getData());
            String sentence = new String(receved) ;
            sentence = sentence.replace("�", "");
            System.out.println("RECEIVED: " + sentence.trim());
            String IP =receivePacket.getAddress().getHostAddress().toString();
            System.out.println("RECEIVED: from "+ IP);
            String puts ="L_"+sentence.trim();
            ChatDict chatdict = ChatDict.getInstance();
            Intent intent = new Intent("custom-event-name");
            intent.putExtra("message", puts);
            //LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            chatdict.addChat(IP,puts);
                // ((ChatDict) ((Activity)context).getApplication()).setChat(IP,puts);
        }

    }
}
