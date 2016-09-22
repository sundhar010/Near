package com.example.chaitanya.test;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.net.*;
import java.io.*;
public class MainActivity extends AppCompatActivity {


    final Context context = this;
    private Button button_submit;
    private  EditText edit_name ;
    private Button prompt_button;
    private EditText prompt_result;
    private static String IP_tracker ;//IP of tracker
    private static final int port_tracker_send_name = 6066;//port on which tracker listens for names

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        button_submit=(Button)findViewById(R.id.button);
        edit_name = (EditText)findViewById(R.id.editText);




        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                IP_tracker=(userInput.getText().toString());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Toast.makeText(getApplicationContext(), "Tails IP required", Toast.LENGTH_LONG).show();
                                finish();
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();


        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name;
                name = edit_name.getText().toString();
                edit_name.setText("");
                send_my_IP_name(name,IP_tracker,port_tracker_send_name);//This function will send the name that user has entered to the tracker
                startActivity(new Intent(MainActivity.this,ListNearsActivity.class));

            }
        });
    }

    private void send_my_IP_name(String name,String IP , int port) {
        SendMyName s = new SendMyName(name,IP,port);
        s.execute();

    }
}
