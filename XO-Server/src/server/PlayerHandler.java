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
            System.out.println(json);
            JsonObject jsonObject = Utils.toJson(json);
            if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.SIGN_UP){
                JsonObject response = DBQueries.signUp(json);
                if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_SUCCESSED)
                {
                    printStream.println(jsonObject.toString());
                    this.start();
                    Server.addOnlinePlayer(this);

                } else if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_FAILED)
                {
                    printStream.println(jsonObject.toString());
                    this.socket.close();
                    this.stop();

                }

            } else if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.LOGIN){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
