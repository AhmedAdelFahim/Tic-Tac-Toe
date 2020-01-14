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
import java.sql.SQLException;

import static db.Tables.player.SCORE;

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
            System.out.println(json);
            JsonObject jsonObject = Utils.toJson(json);
            JsonObject response = null;

            if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.SIGN_UP){
                response = DBQueries.signUp(json);


            } else if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.LOGIN){
                response = DBQueries.login(json);
            }
            else if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())==Constant.INVITE){
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

            if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_SUCCESSED)
            {
                printStream.println(response.toString());
                JsonObject playerDataJsonObject = response.get(Constant.PLAYER_DATA_KEY).getAsJsonObject();
                Player playerData = preparePlayerData(playerDataJsonObject);
                Server.addOnlinePlayersData(playerData);
                System.out.println(response.toString());
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
        System.out.println(playerDataJsonObject);
        id = Integer.parseInt(playerDataJsonObject.get(Tables.player.ID).toString());
        int score = Integer.parseInt(playerDataJsonObject.get(SCORE).toString());
        String fName = playerDataJsonObject.get(Tables.player.FIRST_NAME).toString();
        String lName = playerDataJsonObject.get(Tables.player.LAST_NAME).toString();
        String imageURL = playerDataJsonObject.get(Tables.player.IMAGE_URL).toString();
        String userName = playerDataJsonObject.get(Tables.player.USER_NAME).toString();
        Player player = new Player(id,fName,lName,userName,imageURL,score);
        player.setStatus(Constant.ONLINE_STATUS);

        return player;
    }

    public  void sendOnlinePlayers(String json){
        //System.out.println("sendOnlinePlayers#"+id+" : "+json);
        printStream.println(json);
    }

    public int getPlayerId() {
        return id;
    }

    @Override
    public void run() {
        while (true){
            try {
                String json = dataInputStream.readLine();
                JsonObject jsonObject = Utils.toJson(json);
                if(Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.UPDATE_SCORE){
                    System.out.println("FDSAFDSF");
                }
                switch (Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())){
                    case Constant.LOGOUT:
                        JsonObject response = DBQueries.logout(json);
                        if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_SUCCESSED) {
                            printStream.println(response.toString());
                            //System.out.println(response.toString());
                            Server.removeOnlinePlayersData(id);
                            Server.removeOnlinePlayerHandler(this);
                            socket.close();
                            this.stop();

                        } else if(Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString())==Constant.STATUS_CODE_FAILED) {
                            printStream.println(response.toString());
                        }
                        break;
                    case Constant.UPDATE_SCORE:
                        System.out.println("score updated");
                        DBQueries.updatePlayerScore(getPlayerId(),Integer.parseInt(jsonObject.get(Constant.SCORE_KEY).toString()));
                        break;
                    case Constant.BUSY_STATUS:
                        System.out.println("status updated");
                        DBQueries.changeStatus(getPlayerId(),0);
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
