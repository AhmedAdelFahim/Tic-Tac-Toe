package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import utils.Constant;
import utils.Utils;
import viewmodel.PlayModeViewModel;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;

public class ClientSideHandler {
    private static DataInputStream dataInputStream;
    private static PrintStream printStream;
    private static Socket socket;
    private static ClientSideHandler clientSideHandler = null;
    private Thread handler;
    public static synchronized ClientSideHandler getInstance(){
        if(clientSideHandler == null) {
            clientSideHandler = new ClientSideHandler();
        }
        return clientSideHandler;
    }
    public boolean signUp(String json)
    {
        try {
            socket = new Socket("127.0.0.1",5000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            printStream.println(json);
            JsonObject jsonObject = Utils.toJson(dataInputStream.readLine());
            if(jsonObject.has(Constant.STATUS_CODE_KEY)&&Integer.parseInt(jsonObject.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_SUCCESSED)
            {
                handler();
                return true;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


public boolean logIn(String json)
    {
        try {
            socket = new Socket("127.0.0.1",5000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            printStream.println(json);
            JsonObject jsonObject = Utils.toJson(dataInputStream.readLine());
            if(jsonObject.has(Constant.STATUS_CODE_KEY)&&Integer.parseInt(jsonObject.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_SUCCESSED)
            {
                handler();
                return true;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void updateScore(){
        HashMap<String,Object> map = new HashMap<>();
        map.put(Constant.REQUEST_TYPE,Constant.UPDATE_SCORE);
        printStream.println(Utils.toJson(map));
        System.out.println("from client handler");
    }

    public static void updateStatus(int status){
        HashMap<String,Object> map = new HashMap<>();
        map.put(Constant.REQUEST_TYPE,status);
        String request = Utils.toString(map);
        printStream.println(Utils.toJson(request));
        System.out.println("status updated");
    }

    private void handler(){
        handler = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String json = dataInputStream.readLine();
                        JsonObject jsonObject = Utils.toJson(json);
                        //System.out.println(json);
                        if (jsonObject.has(Constant.REQUEST_TYPE) && Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.ONLINE_PLAYERS_DATA) {
                            JsonArray onlinePlayers = jsonObject.getAsJsonArray(Constant.ONLINE_PLAYER_DATA_KEY);
                            PlayModeViewModel.addOnlinePlayer(onlinePlayers);
                        }
                        System.out.println();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        handler.start();
    }
}
