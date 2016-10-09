package com.project.near.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NearAlive extends Thread {
    private ServerSocket serverSocket;
    public NearAlive(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Yes I am alive " + server.getLocalSocketAddress());
                in.close();
                out.close();
                server.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
