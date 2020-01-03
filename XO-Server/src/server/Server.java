package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket;
    private static ArrayList<PlayerHandler> onlinePlayers;
    public Server() {
        try {
            serverSocket = new ServerSocket(5000);
            onlinePlayers = new ArrayList<>();
            while (true) {
                Socket socket = serverSocket.accept();
                new PlayerHandler(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String [] args){
        new Server();
    }

    public static void addOnlinePlayer(PlayerHandler player){
        onlinePlayers.add(player);
        System.out.println("Onlines " +onlinePlayers.size());
    }

    public static void removeOnlinePlayer(PlayerHandler player){
        onlinePlayers.remove(player);
        System.out.println("Onlines " +onlinePlayers.size());
    }
}
