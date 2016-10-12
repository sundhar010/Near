package com.project.near.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import android.content.Context;

import android.widget.ListView;

public class ListNearsActivity extends AppCompatActivity{

    ListView mListView;
    public static final int PortRcvDict = 6067;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Nears Present");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3cb879")));
        setContentView(R.layout.activity_list_nears);
        mListView = (ListView) findViewById(R.id.nears_list);
        try {
            Thread thread = new NearAlive(6068);
            thread.start();
            Thread t = new ThreadToRcvDict(PortRcvDict,mListView,this);
            t.start();
        }catch(IOException e) {
            e.printStackTrace();
        }

    }
    public void setdict(String[] Nears_names){
        System.out.println("setdict");
        ((ChatDict) this.getApplication()).initChatDict(Nears_names);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}

class ThreadToRcvDict extends Thread {
    private ServerSocket serverSocket;
    ListView mListView;
    Context context;

    public ThreadToRcvDict(int port,ListView mListView,Context context) throws IOException {
        serverSocket = new ServerSocket(port);
        this.mListView=mListView;
        this.context=context;


    }

    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                final String message;
                message = in.readUTF();
                final String[] Nears_names=message.split(";");
               // ListNearsActivity listna = new ListNearsActivity();
                //((ListNearsActivity)listna).setdict(Nears_names);
                ((ChatDict) ((Activity)context).getApplication()).initChatDict(Nears_names);

                Utils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mListView.setAdapter(new ListAdaptor(context,Nears_names    ));
                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                    long arg3) {
                                Intent I = new Intent(context,Chat.class);
                                I.putExtra("name",Nears_names[arg2] );
                                Log.d("############","Items " +  Nears_names[arg2] );
                                context.startActivity(I);

                            }
                        });
                    }
                });
                String str = "";
                String Str = new String(message);
                int i=0;
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for updating the list " + server.getLocalSocketAddress());
                in.close();
                out.close();
                server.close();

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }
    }
}

 class Utils {

    public static void runOnUiThread(Runnable runnable){
        final Handler UIHandler = new Handler(Looper.getMainLooper());
        UIHandler .post(runnable);
    }


 }
