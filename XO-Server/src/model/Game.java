package model;

import db.Tables;
import db.Tables.*;

public class Game {

    private int id;
    private int hostId;
    private int guestID;
    private String gameBoard;

    public Game(int id, int hostId, int guestID,String gameBoard ) {
        this.id = id;
        this.hostId = hostId;
        this.guestID = guestID;
        this.gameBoard = gameBoard;

    }

    @Override
    public String toString() {
        return "{" + saved_game.ID + " = " + id
                + " , " + saved_game.HOST_ID + " = " + hostId
                + " , " + saved_game.GUEST_ID + " = " + guestID
                +" , " + saved_game.GAME_BOARD + " = " + gameBoard + "}";
    }
}
