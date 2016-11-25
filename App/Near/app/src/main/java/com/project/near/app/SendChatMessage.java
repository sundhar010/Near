package com.project.near.app;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SendChatMessage extends AsyncTask<Void, Void, Void> {
    String IP;
    String message;
    int Port;
    SendChatMessage(String IP,String message){
        this.IP = IP;
        this.Port = 6069;
        this.message = message;
    }
    protected Void doInBackground(Void... arg0) {

        try {
            DatagramSocket clientSocket = new DatagramSocket();
            IP=IP.replaceAll("\\s+","");
            System.out.println("sending UDP to"+IP);
            InetAddress IPAddress = InetAddress.getByName(IP);
            byte[] sendData = new byte[1024];
            sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Port);
            clientSocket.send(sendPacket);
            System.out.println("UDP snet "+sendData);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null ;
    }
}
