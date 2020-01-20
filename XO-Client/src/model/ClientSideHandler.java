package model;

import TicTacToe.Board;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.App;
import ui.PlayModeController;
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
import static ui.PlayModeController.invitationJason;
import ui.PlayScreenView;
import ui.SavedGamesController;

import viewmodel.InvitationViewModel;
import viewmodel.SavedGamesViewModel;

public class ClientSideHandler {

    private static BufferedReader dataInputStream;
    private static PrintStream printStream;
    private static Socket socket;
    private static ClientSideHandler clientSideHandler = null;
    private Thread handler;
    private Player currentPlayer;
    private int OtherPlayerMove = -1;

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
                        JsonObject jsonObject = Utils.toJson(json);
                        System.out.println(json);
                        if (jsonObject.has(Constant.REQUEST_TYPE)
                                && Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.ONLINE_PLAYERS_DATA) {
                            JsonArray onlinePlayers = jsonObject.getAsJsonArray(Constant.ONLINE_PLAYER_DATA_KEY);
                            PlayModeViewModel.addOnlinePlayer(onlinePlayers);
                        } else if (jsonObject.has(Constant.REQUEST_TYPE)
                                && Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.INVITE) {
                            PlayModeController.OtherPlayer = jsonObject.get(Constant.SENDER_NAME_KEY).toString();
                            PlayModeController.OtherPlayerId = jsonObject.get(Constant.SENDER_ID_KEY).toString();
                            System.out.println("NEW INVITATION FROM" + PlayModeController.OtherPlayer);
                            InvitationViewModel.handleInvitation(jsonObject);
                        } else if (jsonObject.has(Constant.REQUEST_TYPE)
                                && Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.ACCEPT_INVITATION) {
                            InvitationViewModel.resetCurrentInviteScreenflag();
                            PlayModeController.OtherPlayer = jsonObject.get(Constant.SENDER_NAME_KEY).toString();
                            PlayModeController.OtherPlayerId = jsonObject.get(Constant.SENDER_ID_KEY).toString();
                            System.out.println("the Other Player Is " + PlayModeController.OtherPlayer);
                            PlayScreenView.setModeToPlayers();
                            PlayScreenView.setToHost();
                            System.out.println(App.CurrentStage);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    FXMLLoader fxmlLoader = new FXMLLoader(PlayModeController.class.getResource("PlayScreen.fxml"));
                                    try {
                                        Parent root = fxmlLoader.load();
                                        Scene scene = new Scene(root, 800, 500);
                                        Stage stage = App.CurrentStage;
                                        System.out.println(stage);
                                        stage.setScene(scene);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });

                            System.out.println("ACCEPTED INVITATION");
                            System.out.println(jsonObject);
                        } else if (jsonObject.has(Constant.REQUEST_TYPE)
                                && Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.DECLINE_INVITATION) {
                            System.out.println("Declined INVITATION");
                            PlayModeController.OtherPlayer = jsonObject.get(Constant.SENDER_NAME_KEY).toString();
                            InvitationViewModel.declineInvitation(jsonObject);
                        } else if (jsonObject.has(Constant.REQUEST_TYPE)
                                && Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.GAME_MOVE) {
                            OtherPlayerMove = jsonObject.get(Constant.MOVE_POSTION).getAsInt();
                        } else if (jsonObject.has(Constant.REQUEST_TYPE)
                                && Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.SAVED_GAMES) {
//                           System.out.println(jsonObject);
                            System.out.println("this is saved games");

                            SavedGamesViewModel.savedGames(jsonObject.getAsJsonArray(Constant.GAME_DATA_KEY));
                        } else if (jsonObject.has(Constant.REQUEST_TYPE)
                                && Integer.parseInt(jsonObject.get(Constant.REQUEST_TYPE).toString()) == Constant.LOGOUT_RESPONSE) {
                            if (jsonObject.has(Constant.STATUS_CODE_KEY)
                                    && Integer.parseInt(jsonObject.get(Constant.STATUS_CODE_KEY).toString()) == Constant.STATUS_CODE_SUCCESSED) {
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

    public boolean handelSavedGames(String json) {
        try {
            printStream.println(json);
        } catch (Exception ex) {
            Logger.getLogger(ClientSideHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean sendGameMove(String json) {
        System.out.println(json);
        try {
            printStream.println(json);
        } catch (Exception ex) {
            Logger.getLogger(ClientSideHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public int getOtherPlayerMove() {
        return OtherPlayerMove;
    }

    public void setOtherPlayerMove() {
        OtherPlayerMove = -1;
    }

    public void logout(String json) {
        printStream.println(json);
    }

    public void getOnlinePlayersRequest(){
        HashMap<String,Object> map = new HashMap<>();
        map.put(Constant.REQUEST_TYPE,Constant.ONLINE_PLAYERS_REQUSEST);
        printStream.println(Utils.toString(map));
    }

    private void destroy() {
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
