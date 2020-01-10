/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

/**
 *
 * @author islam salah
 */
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.DataOutputStream;
import java.util.HashMap;

public class ClientHndeller {

    private Socket mySocket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private PrintStream ps;
    private int id;

    
    JsonObject request = null;

    public ClientHndeller() {
        try {
            mySocket = new Socket("127.0.0.1", 5555);
            dis = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
            ps.println("Ping.... ");
            String replayMsg = dis.readLine();
            System.out.println(replayMsg);
            
        } catch (Exception e) {
            System.out.println("Error Creating Connection");
            System.out.println(e);
        }
        
        finally{
            try {
//            dis.close();
//            ps.close();
//            mySocket.close();
        } catch (Exception e) {
                System.out.println("Error Closing connection");
        }
        }
        

    }

    
    
    public void clientLogIn(String userName, String passWord) {
        System.out.println("Enter Handling Successfully");
        HashMap<String,Object> res = new HashMap<>();
        res.put("request_type", "1");
        res.put("userName", userName);
        res.put("passWord", passWord);
        System.out.println(res);
        ps.println(Utils.toString(res));

    }

   

   
}
