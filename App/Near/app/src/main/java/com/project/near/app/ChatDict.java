package com.project.near.app;


import android.app.Application;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.io.Serializable;


public class ChatDict {
    private static ChatDict instance;

    public static  Map chats;
    public static  ChatDict getInstance(){
        if(instance==null){
            instance=new ChatDict();
        }
        return instance;
    }
    public void initChatDict( String [] NearNames){
        chats =new ConcurrentHashMap<String, List<String> >();
        for (String temp : NearNames) {
            String [] parts = temp.split("-");
            List<String> chat = new ArrayList<String>();
            String ip = parts[1].replace(" ","");
            chats.put(ip, chat);
            System.out.println(parts[1]+" in initdict");
        }
    }

    public List<String> getChat(String IP) {
        System.out.println(IP+" in getchat");
        List<String> chat = (List<String>) chats.get(IP);
        for (String pmsg : chat) {
            System.out.println(pmsg+" In get chat");
        }
        return chat;
    }

    public void setChat(String IP,String newMsg) {
        System.out.println(IP+newMsg+" setChat");
        ((List<String>)chats.get(IP)).add(newMsg);
        System.out.println("fine here setChat");
    }
    public void addChat(String IP,String newMsg) {
        System.out.println("this : "+ this);
        System.out.println("ADD IP:"+ IP);
        Set<String> keys = chats.keySet();
        for(String key: keys){
            System.out.println("RECV:"+key);
        }
        System.out.println("list obj : "+((List<String>)chats.get(IP)));
        ((List<String>)chats.get(IP)).add(newMsg);
    }

}