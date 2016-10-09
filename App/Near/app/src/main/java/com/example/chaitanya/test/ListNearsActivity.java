package com.example.chaitanya.test;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListNearsActivity extends AppCompatActivity{

    TextView Texttest;
    ListView mListView;
    Activity A = this;

    public static final int PortRcvDict = 6067;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_list_nears);
         Texttest =(TextView) findViewById(R.id.textView);

        mListView = (ListView) findViewById(R.id.list);

        try {
            Thread t = new ThreadToRcvDict(PortRcvDict, Texttest,mListView);
            t.start();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}

class ThreadToRcvDict extends Thread {
    private ServerSocket serverSocket;
    TextView Testtext;
    ListView mListView;
    Activity A;
    public ThreadToRcvDict(int port, TextView textview,ListView mListView) throws IOException {
        serverSocket = new ServerSocket(port);
        this.Testtext = textview;
        this.mListView=mListView;

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
                String[] Nears_names = null;
                System.out.println(message+"****");

                Utils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Testtext.setText(message);

                    }
                });
                String str = "";
                String Str = new String(message);
                int i=0;
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                        + "\nGoodbye!");
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
