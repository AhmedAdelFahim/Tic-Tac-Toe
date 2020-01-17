package model;

import TicTacToe.Board;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import ui.PlayScreenView;
import utils.Constant;
import utils.Utils;
import viewmodel.LogoutViewModel;
import viewmodel.PlayModeViewModel;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import viewmodel.InvitationViewModel;

public class ClientSideHandler {
    private static BufferedReader dataInputStream;
    private static PrintStream printStream;
    private static Socket socket;
    private static ClientSideHandler clientSideHandler = null;
    private Thread handler;
    private Player currentPlayer;

    public static synchronized ClientSideHandler getInstance() {
        if (clientSideHandler == null) {
            clientSideHandler = new ClientSideHandler();
        }
        return clientSideHandler;
    }

    public boolean signUp(String json) {
        try {
            socket = new Socket("127.0.0.1", 5000);
            dataInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream());
            printStream.println(json);

            JsonObject jsonObject = Utils.toJson(dataInputStream.readLine());
            if (jsonObject.has(Constant.STATUS_CODE_KEY) && Integer.parseInt(jsonObject.get(Constant.STATUS_CODE_KEY).toString()) == Constant.STATUS_CODE_SUCCESSED) {
                handler();
                return true;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean logIn(String json) {
        try {
            socket = new Socket("127.0.0.1", 5000);
            dataInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            printStream.println(json);
            JsonObject jsonObject = Utils.toJson(dataInputStream.readLine());
            if (jsonObject.has(Constant.STATUS_CODE_KEY) && Integer.parseInt(jsonObject.get(Constant.STATUS_CODE_KEY).toString()) == Constant.STATUS_CODE_SUCCESSED) {
                handler();
                currentPlayer = getCurrentPlayerData(jsonObject);
                return true;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void updateScore() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.REQUEST_TYPE, Constant.UPDATE_SCORE);
        String request = Utils.toString(map);
        printStream.println(request);
    }

    public static void updateStatus(int status) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.REQUEST_TYPE, status);
        String request = Utils.toString(map);
        printStream.println(request);
    }

    public static void saveGame(String board) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.REQUEST_TYPE, Constant.SAVE_GAME);
        map.put(Constant.GAME_BOARD, board);
        String request = Utils.toString(map);
        printStream.println(request);
    }

    private void handler() {
        handler = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String json = dataInputStream.readLine();
//                        System.out.println(json);
                        JsonObject jsonObject = Utils.toJson(json);
                        if (jsonObject.has(Constant.REQUEST_TYPE) &&
                                Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.ONLINE_PLAYERS_DATA) {
                            JsonArray onlinePlayers = jsonObject.getAsJsonArray(Constant.ONLINE_PLAYER_DATA_KEY);
                            PlayModeViewModel.addOnlinePlayer(onlinePlayers);
                        } else if (jsonObject.has(Constant.REQUEST_TYPE) &&
                                Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.INVITE) {
                             System.out.println("NEW INVITATION");
                            InvitationViewModel.handleInvitation(jsonObject);
                        } else if (jsonObject.has(Constant.REQUEST_TYPE) &&
                                Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.ACCEPT_INVITATION) {
                             System.out.println("ACCEPTED INVITATION");
                            System.out.println(jsonObject);
                        }else if (jsonObject.has(Constant.REQUEST_TYPE) &&
                                Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.DECLINE_INVITATION) {
                             System.out.println("Declined INVITATION");
                            InvitationViewModel.declineInvitation(jsonObject);
                        } else if (jsonObject.has(Constant.REQUEST_TYPE) &&
                                Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.LOGOUT_RESPONSE) {
                            if (jsonObject.has(Constant.STATUS_CODE_KEY) &&
                                    Integer.parseInt(jsonObject.get(Constant.STATUS_CODE_KEY).toString()) == Constant.STATUS_CODE_SUCCESSED) {
                                LogoutViewModel.setToSignUpFlag(true);
                                destroy();
                            } else {
                                LogoutViewModel.setToSignUpFlag(false);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        handler.start();
    }


    private Player getCurrentPlayerData(JsonObject playerDataJsonObject) {
        int id = Integer.parseInt(playerDataJsonObject.get("player_data").getAsJsonObject().get(Constant.ID_KEY).toString());
        int score = Integer.parseInt(playerDataJsonObject.get("player_data").getAsJsonObject().get(Constant.SCORE_KEY).toString());
        String fName = playerDataJsonObject.get("player_data").getAsJsonObject().get(Constant.FIRST_NAME_KEY).toString();
        String lName = playerDataJsonObject.get("player_data").getAsJsonObject().get(Constant.LAST_NAME_KEY).toString();
        //String imageURL = playerDataJsonObject.get(Tables.player.IMAGE_URL).toString();
        String userName = playerDataJsonObject.get("player_data").getAsJsonObject().get(Constant.USER_NAME_KEY).toString();
        Player player = new Player(id, fName, lName, userName, null, score);

        return player;
    }

    public boolean handelInvitation(String json) {
        try {
            printStream.println(json);
        } catch (Exception ex) {
            Logger.getLogger(ClientSideHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }


    public void logout(String json) {
        printStream.println(json);
    }

    private void destroy(){
        try {
            handler.stop();
            socket.close();
            dataInputStream.close();
            printStream.close();
            clientSideHandler = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
