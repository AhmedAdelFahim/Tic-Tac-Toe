package model;


import com.google.gson.JsonObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.Constant;

public class Player {
    private int id;
    private String firstName;
    private String lastName;
    private StringProperty userName;
    private String password;
    private String imageURL;
    private int status;
    private IntegerProperty score;

    public Player(String userName, int score) {
        this.userName = new SimpleStringProperty(userName);
        this.score = new SimpleIntegerProperty(score);
    }

    public Player(int id, String firstName, String lastName, String userName, String imageURL, int score) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = new SimpleStringProperty(userName);
        this.imageURL = imageURL;

        this.score = new SimpleIntegerProperty(score);
    }

    public Player(String firstName, String lastName, String userName, String password, String imageURL, int score) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = new SimpleStringProperty(userName);
        this.password = password;
        this.imageURL = imageURL;
        this.score = new SimpleIntegerProperty(score);
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
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.setValue( userName);
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
        return score.get();
    }

    public void setScore(int score) {
        this.score.setValue( score);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static Player preparePlayerData(JsonObject playerDataJsonObject){
       // System.out.println(playerDataJsonObject);
        int id = Integer.parseInt(playerDataJsonObject.get(Constant.ID_KEY).toString());
        int score = Integer.parseInt(playerDataJsonObject.get(Constant.SCORE_KEY).toString());
        String fName = playerDataJsonObject.get(Constant.FIRST_NAME_KEY).toString();
        String lName = playerDataJsonObject.get(Constant.LAST_NAME_KEY).toString();
        String imageURL = playerDataJsonObject.get(Constant.IMAGE_URL_KEY).toString();
        String userName = playerDataJsonObject.get(Constant.USER_NAME_KEY).toString();
        int status = Integer.parseInt(playerDataJsonObject.get(Constant.STATUE_KEY).toString());
        Player player = new Player(id,fName,lName,userName,imageURL,score);
        player.setStatus(status);

        return player;
    }

    @Override
    public String toString() {
        return "{"+ Constant.FIRST_NAME_KEY + " = " + firstName +
                " , " + Constant.LAST_NAME_KEY + " = " + lastName +
                " , " + Constant.STATUE_KEY + " = " + status +
                " , " + Constant.ID_KEY + " = " + id +
                " , " + Constant.IMAGE_URL_KEY + " = " + imageURL +
                " , " + Constant.SCORE_KEY + " = " + score.get() +
                " , " + Constant.USER_NAME_KEY + " = " + userName.get() + "}";
    }
}
