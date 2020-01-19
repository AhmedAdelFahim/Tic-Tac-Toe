package model;

import com.google.gson.JsonObject;
import utils.Constant;

public class Game {

    private int id;
    private int hostId;
    private int guestID;
    private String userName;
    private String gameBoard;

    public Game(int id, int hostId, int guestID, String gameBoard) {
        this.id = id;
        this.hostId = hostId;
        this.guestID = guestID;
        this.gameBoard = gameBoard;

    }

    public Game(int id, String userName, String gameBoard) {
        this.id = id;
        this.userName = userName;
        this.gameBoard = gameBoard;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public int getGuestID() {
        return guestID;
    }

    public void setGuestID(int guestID) {
        this.guestID = guestID;
    }

    public String getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(String gameBoard) {
        this.gameBoard = gameBoard;
    }

    public static Game prepareGameData(JsonObject savedGAmesDataJsonObject){
        int id = Integer.parseInt(savedGAmesDataJsonObject.get(Constant.ID_KEY).toString());
        String userName = savedGAmesDataJsonObject.get(Constant.USER_NAME_KEY).toString();
        String gameBoard = savedGAmesDataJsonObject.get("game_board").toString();

        Game game = new Game(id,userName,gameBoard);

        return game;
    }

     @Override
    public String toString() {
        return "{" + Constant.ID + " = " + id
                + " , " + Constant.USER_NAME_KEY + " = " + userName
                + " , " + Constant.GAME_BOARD + " = " + gameBoard + "}";
    }
}
