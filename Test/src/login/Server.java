/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.google.gson.JsonObject;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author islam salah
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    
    ServerSocket MyServerSocket;
    Socket s;
    DataInputStream dis;
    PrintStream ps;
    
    public Server(){
        try {
            MyServerSocket = new ServerSocket(5555);
            s = MyServerSocket.accept();
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            String msg = dis.readLine();
            System.out.println(msg);
            ps.println("Pong !");
            System.out.println("Server Started ..");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        finally{
            try {
            dis.close();
            ps.close();
            s.close();
            MyServerSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        
    }
    
    public static void main(String[] args){
        Server s = new Server();
    }
}

 

