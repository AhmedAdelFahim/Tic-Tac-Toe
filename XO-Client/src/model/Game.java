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

    @Override
    public String toString() {
        return "{" + Constant.ID + " = " + id
                + " , " + Constant.HOST_ID + " = " + hostId
                + " , " + Constant.GUEST_ID + " = " + guestID
                +" , " + Constant.GAME_BOARD + " = " + gameBoard + "}";
    }
}
