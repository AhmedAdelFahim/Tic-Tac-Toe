package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.DBQueries;
import db.Tables;
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
    private int id;
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
                if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_SUCCESSED)
                {
                    printStream.println(response.toString());
                    id = Integer.parseInt(response.get(Constant.PLAYER_DATA).getAsJsonObject().get(Tables.player.ID).toString());
                    System.out.println(response.toString());
                    this.start();
                    Server.addOnlinePlayer(this);

                } else if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_FAILED)
                {
                    printStream.println(response.toString());
                    this.socket.close();
                    this.stop();

                }

            } else if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.LOGIN){

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                String json = dataInputStream.readLine();
                JsonObject jsonObject = Utils.toJson(json);
                if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.LOGOUT){
                    JsonObject response = DBQueries.logout(json);
                    if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_SUCCESSED) {
                        printStream.println(response.toString());
                        System.out.println(response.toString());
                        Server.removeOnlinePlayer(this);
                        socket.close();
                        this.stop();

                    } else if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_FAILED) {
                        printStream.println(response.toString());
                    }
                } else {
                    // playing mode
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
