package com.example.chaitanya.test;

import android.location.LocationManager;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.net.*;
import java.io.*;
public class MainActivity extends AppCompatActivity {
    private Button button_submit;
    private  EditText edit_name ;
    private static final String IP_tracker = "192.168.1.34";//IP of tracker
    private static final int port_tracker_send_name = 6066;//port on which tracker listens for names

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        button_submit=(Button)findViewById(R.id.button);
        edit_name = (EditText)findViewById(R.id.editText);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name;
                name = edit_name.getText().toString();
                edit_name.setText("");
                send_my_IP_name(name,IP_tracker,port_tracker_send_name);//This function will send the name that user has entered to the tracker
            }
        });
    }

    private void send_my_IP_name(String name,String IP , int port) {
        SendMyName s = new SendMyName(name,IP,port);
        s.execute();

    }
}

