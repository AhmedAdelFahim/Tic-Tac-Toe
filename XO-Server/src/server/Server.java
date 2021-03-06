package server;

import model.Player;
import utils.Constant;
import utils.Utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.function.BiConsumer;

public class Server {

    private ServerSocket serverSocket;
    private static Hashtable<Integer,PlayerHandler> onlinePlayersHandler;
    public static Hashtable<Integer,Player> onlinePlayersData;
    //public ArrayList<String> onlineTest ;

    public  void startServer() {
        try {
            serverSocket = new ServerSocket(5000);
           // onlinePlayers = new ArrayList<>();
           // onlineTest = new ArrayList<>();
            onlinePlayersHandler = new Hashtable<>();
            onlinePlayersData = new Hashtable<>();


            while (true) {
                Socket socket = serverSocket.accept();
                new PlayerHandler(socket);
            }
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public  void closeServer() throws IOException {
        broadcastServerSleep();
        onlinePlayersData.clear();
        onlinePlayersHandler.clear();
        serverSocket.close();
    }
    
    public static void main(String [] args){
       Server server = new Server();
        server.startServer();
    }

    public static void addOnlinePlayerHandler(PlayerHandler player){
        onlinePlayersHandler.put(player.getPlayerId(),player);
        //System.out.println("Onlines " +onlinePlayersHandler.size());
    }

    public static void removeOnlinePlayerHandler(PlayerHandler player){
        onlinePlayersHandler.remove(player.getPlayerId());
        //System.out.println("Onlines " +onlinePlayersHandler.size());
    }
    
    public static PlayerHandler getOnlinePlayerHandler(int id){
        return onlinePlayersHandler.get(id);
    }

    public static Hashtable<Integer, PlayerHandler> getAllOnlinePlayerHandler(){
        return onlinePlayersHandler;
    }

    public static void addOnlinePlayersData(Player player) {
        onlinePlayersData.put(player.getId(),player);
    }

    public static Player getOnlinePlayersData(int id) {
        return onlinePlayersData.get(id);
    }

    //////////*****************************************
     public static Hashtable getAllOnlinePlayersData() {
        return onlinePlayersData;
    }
    /////////////*******************************
    
    
    public static void removeOnlinePlayersData(int playerId) {
        onlinePlayersData.remove(playerId);
    }

    public static void broadcastServerSleep(){
        //System.out.println("Broadcast");
        HashMap<String,Object> map = new HashMap<>();
        map.put(Constant.REQUEST_TYPE,Constant.SERVER_SLEEP);

        onlinePlayersHandler.forEach(new BiConsumer<Integer, PlayerHandler>() {
            @Override
            public void accept(Integer integer, PlayerHandler playerHandler) {
                    playerHandler.sendServerSleep(Utils.toString(map));
            }
        });

    }

    public static void broadcastOnlinePlayers(){
        System.out.println("Broadcast");
        HashMap<String,Object> map = new HashMap<>();
        map.put(Constant.REQUEST_TYPE,Constant.ONLINE_PLAYERS_DATA);
        ArrayList<String> playersDataJSONObject = new ArrayList<>();
        onlinePlayersData.forEach(new BiConsumer<Integer, Player>() {
            @Override
            public void accept(Integer integer, Player player) {
                if(player.getStatus() == Constant.ONLINE_STATUS || player.getStatus() == Constant.BUSY_STATUS)
                playersDataJSONObject.add(player.toString());

            }
        });
        map.put(Constant.ONLINE_PLAYER_DATA_KEY,playersDataJSONObject);

        onlinePlayersHandler.forEach(new BiConsumer<Integer, PlayerHandler>() {
            @Override
            public void accept(Integer integer, PlayerHandler playerHandler) {
                if(getOnlinePlayersData(playerHandler.getPlayerId()).getStatus()==Constant.ONLINE_STATUS) {
                    playerHandler.sendOnlinePlayers(Utils.toString(map));
                }
            }
        });

    }
    
}
