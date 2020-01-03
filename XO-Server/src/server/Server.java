package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    public Server() {
        try {
            serverSocket = new ServerSocket(5000);
            while (true) {
                Socket socket = serverSocket.accept();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
