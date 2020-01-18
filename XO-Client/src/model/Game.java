package model;

import utils.Constant;



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
        return "{" + Constant.ID + " = " + id
                + " , " + Constant.HOST_ID + " = " + hostId
                + " , " + Constant.GUEST_ID + " = " + guestID
                +" , " + Constant.GAME_BOARD + " = " + gameBoard + "}";
    }
}
