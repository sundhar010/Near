package com.project.near.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;


public class Chat extends AppCompatActivity {
    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private boolean side = true;
    private BroadcastReceiver receiver;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));
        setContentView(R.layout.activity_chat);

        buttonSend = (Button) findViewById(R.id.send);

        listView = (ListView) findViewById(R.id.msgview);
        Intent intent =  getIntent();

        String name    =  intent.getStringExtra("name");
        String [] NameIP = name.split("-");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(NameIP[0]);
        final String IP = NameIP[1].replace(" ","");



   /*      BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

            @Override

            public void onReceive(Context context, Intent intent) {

                String message = intent.getStringExtra("message");
               ReceiveMesg(message);
                //  ... react to local broadcast message

            }

        };*/





        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3cb879")));


        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        return sendChatMessage(IP);

                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                  sendChatMessage(IP);

            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

        setAllChats(IP);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            ReceiveMesg(message);
        }
    };


    private void ReceiveMesg(String message) {
        System.out.println("ReceiveMesg function ****** "+message);
        String [] msg = message.split("_");
        showMessage(msg[1],msg[0]);
    }

    private void setAllChats(String IP) {

        ChatDict chatDict = ChatDict.getInstance();
        List<String> chat = chatDict.getChat(IP);
            System.out.println("Test"+"IP = "+IP);
            for (String pmsg : chat) {
                System.out.println(pmsg+" setallchats");
                String [] msg = pmsg.split("_");
                showMessage(msg[1],msg[0]);
            }


    }

    private void showMessage(String msg, String sider) {
        ;
        boolean bool = true;
        System.out.println("side "+ sider);
        if((sider.replace(" ","")).equals("R")){
        bool = true;
            System.out.println("msg = "+msg+" side = "+sider+" bool = "+bool);
        }
        else {
            if ((sider.replace(" ","")).equals("L")) {
                bool = false;
                System.out.println("msg = " + msg + " side = " + sider + " bool = " + bool);
            }
        }
        chatArrayAdapter.add(new ChatMessage(bool,msg));
    }

    private boolean sendChatMessage(String IP)  {
        side = true;
        String s= chatText.getText().toString();
        chatArrayAdapter.add(new ChatMessage(side,s ));
        chatText.setText("");
        SendChatMessage sender;
        sender = new SendChatMessage(IP,s);
        sender.execute();
        String puts = "R_"+s;
        System.out.println(IP+" "+puts+" sendchatmessage button press");
       // showMessage(s,"R");
        ChatDict chatdict = ChatDict.getInstance();
        chatdict.setChat(IP,puts);

        //((ChatDict) (this).getApplication()).setChat(IP,puts);
        return true;
    }
    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}

