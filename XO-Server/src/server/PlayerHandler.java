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
import java.sql.SQLException;
import java.util.HashMap;

public class PlayerHandler extends Thread {

    private Socket socket;
    private DataInputStream dataInputStream;
    private PrintStream printStream;
    private int host_id;
    private int guest_id = -1;

    public PlayerHandler(Socket socket) {
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            String json = dataInputStream.readLine();
            JsonObject jsonObject = Utils.toJson(json);
            JsonObject response = null;

            if (Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.SIGN_UP) {
                response = DBQueries.signUp(json);

            } else if (Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.LOGIN) {
                response = DBQueries.login(json);
            }

            if (Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString()) == Constant.STATUS_CODE_SUCCESSED) {
                printStream.println(response.toString());
                JsonObject playerDataJsonObject = response.get(Constant.PLAYER_DATA_KEY).getAsJsonObject();
                Player playerData = preparePlayerData(playerDataJsonObject);
                Server.addOnlinePlayersData(playerData);
                this.start();
                Server.addOnlinePlayerHandler(this);
                Server.broadcastOnlinePlayers();

            } else if (Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString()) == Constant.STATUS_CODE_FAILED) {
                printStream.println(response.toString());
                this.socket.close();
                this.stop();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Player preparePlayerData(JsonObject playerDataJsonObject) {
        host_id = Integer.parseInt(playerDataJsonObject.get(Tables.player.ID).toString());
        int score = Integer.parseInt(playerDataJsonObject.get(SCORE).toString());
        String fName = playerDataJsonObject.get(Tables.player.FIRST_NAME).toString();
        String lName = playerDataJsonObject.get(Tables.player.LAST_NAME).toString();
        String imageURL = playerDataJsonObject.get(Tables.player.IMAGE_URL).toString();
        String userName = playerDataJsonObject.get(Tables.player.USER_NAME).toString();
        Player player = new Player(host_id, fName, lName, userName, imageURL, score);
        player.setStatus(Constant.ONLINE_STATUS);

        return player;
    }

    public void sendOnlinePlayers(String json) {
        printStream.println(json);
    }

    public int getPlayerId() {
        return host_id;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String json = dataInputStream.readLine();
                //System.out.println(json);
                JsonObject jsonObject = Utils.toJson(json);
                switch (Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString())) {
                    case Constant.LOGOUT:
                        JsonObject response = DBQueries.logout(json);
                        if (Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString()) == Constant.STATUS_CODE_SUCCESSED) {
                            System.out.println(response.toString());
                            printStream.println(response.toString());
                            Server.removeOnlinePlayersData(host_id);
                            Server.removeOnlinePlayerHandler(this);
                            socket.close();
                            this.stop();

                        } else if (Integer.parseInt(response.get(Constant.STATUS_CODE_KEY).toString()) == Constant.STATUS_CODE_FAILED) {
                            printStream.println(response.toString());
                        }
                        break;
                    case Constant.UPDATE_SCORE:
                        DBQueries.updatePlayerScore(host_id);
                        break;
                    case Constant.BUSY_STATUS:
                        System.out.println("update status");
                        DBQueries.changeStatus(host_id, 0);
                        break;
                    case Constant.ONLINE_STATUS:
                        System.out.println("update status");
                        DBQueries.changeStatus(host_id, 1);
                        break;
                    case Constant.SAVE_GAME:
                        DBQueries.saveGame(host_id, guest_id, jsonObject.get(Constant.GAME_BOARD).toString());
                        System.out.println("updated successfully");
                        break;
                    case Constant.INVITE:
                        System.out.println(" INVITE");
                        System.out.println(jsonObject);
                        sendInvitationRequestToSpecificPlayer(jsonObject);
                        break;

                    case Constant.ACCEPT_INVITATION:
                        System.out.println("Accept");
                        System.out.println(jsonObject);
                        sendInvitationRequestToSpecificPlayer(jsonObject);
                        break;
                    case Constant.DECLINE_INVITATION:
                        System.out.println("Decline");
                        System.out.println(jsonObject);
                        sendInvitationRequestToSpecificPlayer(jsonObject);
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

    void sendInvitationRequestToSpecificPlayer(JsonObject jsonInvitation) {

        Player invitedPlayer = Server.getOnlinePlayersData(Integer.parseInt(jsonInvitation.get(Constant.RECIEVER_ID_KEY).toString()));
        PlayerHandler invitedPlayerHandeler = Server.getOnlinePlayerHandler(invitedPlayer.getId());
        invitedPlayerHandeler.printStream.println(jsonInvitation);
    }

}
