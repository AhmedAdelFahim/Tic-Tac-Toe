package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.DBQueries;
import utils.Constant;
import utils.Utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class PlayerHandler extends Thread {

    private Socket socket;
    private DataInputStream dataInputStream;
    private PrintStream printStream;

    public PlayerHandler(Socket socket)
    {
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            String json = dataInputStream.readLine();
            JsonObject jsonObject = Utils.toJson(json);
            if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.SIGN_UP){
                JsonObject response = DBQueries.signUp(json);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private int checkRequestType(JsonObject jsonObject){
        if(jsonObject.get(Constant.REQUEST_TYPE).equals("LOGIN")){
            return Constant.LOGIN;
        } else if(jsonObject.get(Constant.REQUEST_TYPE).equals("LOGOUT")){
            return Constant.LOGOUT;
        } else if(jsonObject.get(Constant.REQUEST_TYPE).equals("SIGN_UP")){
            return Constant.SIGN_UP;
        } else {
            return -2;
        }
    }*/
}
