package model;

import db.Tables;
import db.Tables.*;

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

    @Override
    public String toString() {
        return "{" + saved_game.ID + " = " + id
                + " , " + player.USER_NAME + " = " + userName
                + " , " + saved_game.GAME_BOARD + " = " + gameBoard + "}";
    }
}
