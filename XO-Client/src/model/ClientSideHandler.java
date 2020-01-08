package model;

import com.google.gson.JsonObject;
import utils.Constant;
import utils.Utils;

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
        /*HashMap<String,Object> map = new HashMap<>();

        map.put(Constant.REQUEST_TYPE,Constant.LOGIN);
        //map.put("first_name","ALi");
        //map.put("last_name","Islam");
        map.put("user_name","AAA");
        map.put("password","14255");
*/
        try {
            socket = new Socket("127.0.0.1",5000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            printStream.println(json);
            JsonObject jsonObject = Utils.toJson(dataInputStream.readLine());
            if(jsonObject.has(Constant.STATUS_CODE_KEY)&&Integer.parseInt(jsonObject.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_SUCCESSED)
            {
                return true;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void handler(){
        handler = new Thread(new Runnable() {
            @Override
            public void run() {
                
            }
        });


    }
}
