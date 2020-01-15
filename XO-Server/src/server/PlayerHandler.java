package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.DBQueries;
import db.Tables;
import model.Player;
import utils.Constant;
import utils.Utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import static db.Tables.player.SCORE;
import java.util.HashMap;

public class PlayerHandler extends Thread {

    private Socket socket;
    private DataInputStream dataInputStream;
    private PrintStream printStream;
    private int host_id;
    private int guest_id = -1;
    public PlayerHandler(Socket socket)
    {
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            String json = dataInputStream.readLine();
            JsonObject jsonObject = Utils.toJson(json);
            JsonObject response = null;

            if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.SIGN_UP){
                response = DBQueries.signUp(json);


            } else if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.LOGIN){
                response = DBQueries.login(json);
            }
            

            if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_SUCCESSED)
            {
                printStream.println(response.toString());
                JsonObject playerDataJsonObject = response.get(Constant.PLAYER_DATA_KEY).getAsJsonObject();
                Player playerData = preparePlayerData(playerDataJsonObject);
                Server.addOnlinePlayersData(playerData);
                this.start();
                Server.addOnlinePlayerHandler(this);
                Server.broadcastOnlinePlayers();

            } else if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_FAILED)
            {
                printStream.println(response.toString());
                this.socket.close();
                this.stop();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Player preparePlayerData(JsonObject playerDataJsonObject){
        host_id = Integer.parseInt(playerDataJsonObject.get(Tables.player.ID).toString());
        int score = Integer.parseInt(playerDataJsonObject.get(SCORE).toString());
        String fName = playerDataJsonObject.get(Tables.player.FIRST_NAME).toString();
        String lName = playerDataJsonObject.get(Tables.player.LAST_NAME).toString();
        String imageURL = playerDataJsonObject.get(Tables.player.IMAGE_URL).toString();
        String userName = playerDataJsonObject.get(Tables.player.USER_NAME).toString();
        Player player = new Player(host_id,fName,lName,userName,imageURL,score);
        player.setStatus(Constant.ONLINE_STATUS);

        return player;
    }

    public  void sendOnlinePlayers(String json){
        printStream.println(json);
    }

    public int getPlayerId() {
        return host_id;
    }

    @Override
    public void run() {
        while (true){
            try {
                String json = dataInputStream.readLine();
                System.out.println(json);
                JsonObject jsonObject = Utils.toJson(json);
                switch (Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())){
                    case Constant.LOGOUT:
                        JsonObject response = DBQueries.logout(json);
                        if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_SUCCESSED) {
                            printStream.println(response.toString());
                            Server.removeOnlinePlayersData(host_id);
                            Server.removeOnlinePlayerHandler(this);
                            socket.close();
                            this.stop();

                        } else if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_FAILED) {
                            printStream.println(response.toString());
                        }else if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.INVITE){
                            //json
                        sendInvitationToSpecificPlayer(jsonObject);
                            
                        }
                        else if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.ACCEPT_INVITATION){
                            //json
                        //                 AcceptInvitation(player1,player2)
                        }
                        else if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.DECLINE_INVITATION){
                            //json
                            //DeclineInvitation(player1,player2)
                        }
                        break;
                    case Constant.UPDATE_SCORE:
                        DBQueries.updatePlayerScore(host_id);
                        break;
                    case Constant.BUSY_STATUS:
                        System.out.println("update status");
                        DBQueries.changeStatus(host_id,0);
                        break;
                    case Constant.ONLINE_STATUS:
                        System.out.println("update status");
                        DBQueries.changeStatus(host_id,1);
                        break;
                    case Constant.SAVE_GAME:
                        DBQueries.saveGame(host_id,guest_id,jsonObject.get(Constant.GAME_BOARD).toString());
                        System.out.println("updated successfully");
                        break;
                    default:
                        System.out.println("default case");
                        break;
                }



            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    
    }
    
    
    
    
    
    void sendInvitationToSpecificPlayer(JsonObject jsonInvitation)
    {
        
        Player invitedPlayer=Server.getOnlinePlayersData(Integer.parseInt(jsonInvitation.get(Constant.RECIEVER_ID_KEY).toString()));
        PlayerHandler invitedPlayerHandeler = Server.getOnlinePlayerHandler(invitedPlayer.getId());
        invitedPlayerHandeler.printStream.println(jsonInvitation);
        System.out.println(jsonInvitation);
        System.out.println(invitedPlayerHandeler.getPlayerId()+" **********"+invitedPlayer.getId());
        
    }
    
  
    
//    void broadcastInvation()
//    {
//       //broadCast Invitation to all players;
//        //make a thread function ListenToInvitatios in clientHandler  
//    }
    
    
//    void AcceptInvitation(int player1,int player2)
//    {
//      Start Game for both Players  
//    }
        
    
//   void DeclineInvitation(int player1,int player2)
//    {
//      send A Declination message to the player;  
//    }
    
}
