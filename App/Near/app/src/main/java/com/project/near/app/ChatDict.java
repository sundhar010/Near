package com.project.near.app;


import android.app.Application;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatDict extends Application {

    private Map chats;
    public void initChatDict( String [] NearNames){
        for (String temp : NearNames) {
            String [] parts = temp.split("-");
            List<String> chat = new ArrayList<String>();
            chats =new ConcurrentHashMap<String, List<String> >();
            chats.put(parts[1], chat);
            System.out.println(parts[1]+" in initdict");
        }
    }

    public List<String> getChat(String IP) {
        System.out.println(IP+" in getchat");
        List<String> chat = (List<String>) chats.get(IP);
        return chat;
    }

    public void setChat(String IP,String newMsg) {
        System.out.println(IP+newMsg+" setChat");
        ((List<String>)chats.get(IP)).add(newMsg);
        System.out.println("fine here setChat");
    }
}