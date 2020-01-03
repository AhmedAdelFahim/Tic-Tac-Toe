package test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.DBQueries;
import utils.Constant;
import utils.Utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;

public class Main {

    public static DataInputStream dataInputStream;
    public static PrintStream printStream;
    public static void main(String [] args){
        HashMap<String,Object> map = new HashMap<>();

        map.put(Constant.REQUEST_TYPE,Constant.SIGN_UP);
        map.put("first_name","ALi");
        map.put("last_name","Islam");
        map.put("user_name","QQQ");
        map.put("password","1425");

        try {
            Socket socket = new Socket("127.0.0.1",5000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            printStream.println(Utils.toString(map));
            System.out.println(dataInputStream.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(60000);
            map.put(Constant.REQUEST_TYPE,Constant.LOGOUT);
            printStream.println(Utils.toString(map));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        JsonObject jsonObject = new JsonParser().parse(DBQueries.signUp(map.toString())).getAsJsonObject().toString();
        //System.out.println(new JsonParser().parse(DBQueries.signUp(map.toString())).getAsJsonObject().toString());
    }
}