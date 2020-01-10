package model;


import db.Tables;
import db.Tables.*;

public class Player {
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String imageURL;
    private int status;
    private int score;

    public Player(int id, String firstName, String lastName, String userName, String imageURL, int score) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.imageURL = imageURL;

        this.score = score;
    }

    public Player(String firstName, String lastName, String userName, String password, String imageURL, int score) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.imageURL = imageURL;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{"+player.FIRST_NAME + " = " + firstName +
                " , " + player.LAST_NAME + " = " + lastName +
                " , " + player.STATUE + " = " + status +
                " , " + player.ID + " = " + id +
                " , " + player.IMAGE_URL + " = " + imageURL +
                " , " + player.SCORE + " = " + score +
                " , " + player.USER_NAME + " = " + userName + "}";
    }
}
