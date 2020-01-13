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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSideHandler {
    private static DataInputStream dataInputStream;
    private static PrintStream printStream;
    private static Socket socket;
    private static ClientSideHandler clientSideHandler = null;
    private Thread handler;
    private Player currentPlayer;
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
            System.err.println(jsonObject.toString());
            if(jsonObject.has(Constant.STATUS_CODE_KEY)&&Integer.parseInt(jsonObject.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_SUCCESSED)
            {
                handler();
                   currentPlayer = getCurrentPlayerData(jsonObject);
                return true;
            }
            


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

public Player getCurrentPlayer(){
    return currentPlayer;
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
    
    
//    Thread InvitationThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//               while(true){
//                   try {
//                       JsonObject invitation= Utils.toJson(dataInputStream.readLine());
//                      // if(invitation.request_type == Constatnt.INVITE && invitation.id = this.id){
////                                alertBox----iinvitation 
//                       // }
//                   } catch (IOException ex) {
//                       Logger.getLogger(ClientSideHandler.class.getName()).log(Level.SEVERE, null, ex);
//                   }
//                   
//               }
//            }
//        });
    
    
    
    private Player getCurrentPlayerData(JsonObject playerDataJsonObject){
        //playerDataJsonObject.get("player_data").getAsJsonObject().get(Constant.ID_KEY).toString()
                
        System.out.println(playerDataJsonObject);
        int id = Integer.parseInt( playerDataJsonObject.get("player_data").getAsJsonObject().get(Constant.ID_KEY).toString());
        int score = Integer.parseInt(playerDataJsonObject.get("player_data").getAsJsonObject().get(Constant.SCORE_KEY).toString());
        String fName = playerDataJsonObject.get("player_data").getAsJsonObject().get(Constant.FIRST_NAME_KEY).toString();
        String lName = playerDataJsonObject.get("player_data").getAsJsonObject().get(Constant.LAST_NAME_KEY).toString();
        //String imageURL = playerDataJsonObject.get(Tables.player.IMAGE_URL).toString();
        String userName = playerDataJsonObject.get("player_data").getAsJsonObject().get(Constant.USER_NAME_KEY).toString();
        Player player = new Player(id,fName,lName,userName,null,score);

        return player;
    }
public boolean handelInvitation(String json){
        try {
            //dataInputStream = new DataInputStream(socket.getInputStream());
            printStream.println(json);
            System.out.println(json);
        } catch (Exception ex) {
            Logger.getLogger(ClientSideHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
}


    
}
