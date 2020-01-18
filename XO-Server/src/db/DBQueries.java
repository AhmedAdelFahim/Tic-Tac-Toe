package db;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Tab;
import model.Player;
import utils.Constant;
import utils.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import model.Game;

public class DBQueries {

    /*
     *
     * input params first name , last name , password , user name as string in json format
     *
     * output params if sign up Successed return status_code = 200 and player data
     *               else return status_code = 400
     *
     *  Note : output as Json Object
     */
    public static JsonObject signUp(String json) {
        HashMap<String, Object> res = new HashMap<>();
        int isInserted = 0;
        JsonObject jsonObject = Utils.toJson(json);
        String password = jsonObject.get("password").toString();
        String fName = jsonObject.get("first_name").toString();
        String lName = jsonObject.get("last_name").toString();
        String userName = jsonObject.get("user_name").toString();

        try {
            PreparedStatement signUpStatment = DBConnection.getInstance().prepareStatement("INSERT INTO player (first_name,last_name,password,user_name) VALUES (?,?,?,?)");
            signUpStatment.setString(1, fName);
            signUpStatment.setString(2, lName);
            signUpStatment.setString(3, password);
            signUpStatment.setString(4, userName);
            isInserted = signUpStatment.executeUpdate();
        } catch (SQLException e) {
            //res.put("res_status",400);
            System.out.println(e.getMessage());
            e.printStackTrace();

        }

        if (isInserted == 1) {
            res.put(Constant.STATUS_CODE_KEY, Constant.STATUS_CODE_SUCCESSED);
            res.put("player_data", getPlayerByUserName(json));
        } else {
            res.put(Constant.STATUS_CODE_KEY, Constant.STATUS_CODE_FAILED);
        }
        return Utils.toJson(res);
    }

    /*
     *
     * input params user name as string in json format
     *
     * output params if player existed return player data
     *               else return null
     */
    public static String getPlayerByUserName(String json) {
        JsonObject jsonObject = Utils.toJson(json);
        String userName = jsonObject.get(Tables.player.USER_NAME).toString();
        Player player = null;
        try {
            PreparedStatement selectPlayer = DBConnection.getInstance().prepareStatement("SELECT * FROM player WHERE user_name = ?");
            selectPlayer.setString(1, userName);
            ResultSet resultSet = selectPlayer.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(Tables.player.ID);
            int score = resultSet.getInt(Tables.player.SCORE);
            String fName = resultSet.getString(Tables.player.FIRST_NAME);
            String lName = resultSet.getString(Tables.player.LAST_NAME);
            String imageURL = resultSet.getString(Tables.player.IMAGE_URL);
            player = new Player(id, fName, lName, userName, imageURL, score);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player.toString();
    }

    /*
     *
     * Input params : user_name  as string in json format
     *
     * Output params : if logout Successed return status_code = 200
     *                   else return status_code = 400
     * Note : output as Json Object
     */
    public static JsonObject logout(String json) {
        HashMap<String, Object> res = new HashMap<>();
        res.put(Constant.REQUEST_TYPE, Constant.LOGOUT_RESPONSE);
        try {
            int isUpdated = updatePlayerStatus(json, Constant.OFFLINE_STATUS);

            if (isUpdated == 1) {
                res.put(Constant.STATUS_CODE_KEY, Constant.STATUS_CODE_SUCCESSED);
            } else {
                res.put(Constant.STATUS_CODE_KEY, Constant.STATUS_CODE_FAILED);
            }

        } catch (SQLException e) {
            res.put(Constant.STATUS_CODE_KEY, Constant.STATUS_CODE_FAILED);
            e.printStackTrace();
        }
        return Utils.toJson(res);
    }

    private static int updatePlayerStatus(String json, int status) throws SQLException {
        JsonObject jsonObject = Utils.toJson(json);
        PreparedStatement preparedStatement = DBConnection.getInstance().prepareStatement("UPDATE player SET " + Tables.player.STATUE + " = ? WHERE " + Tables.player.USER_NAME + " = ?");
        preparedStatement.setInt(1, status);
        preparedStatement.setString(2, jsonObject.get(Tables.player.USER_NAME).toString());
        return preparedStatement.executeUpdate();
    }

    public static JsonObject login(String json) {
        HashMap<String, Object> res = new HashMap<>();
        JsonObject jsonObject = Utils.toJson(json);
        String userName = jsonObject.get(Tables.player.USER_NAME).toString();
        String password = jsonObject.get(Tables.player.PASSWORD).toString();
        Player player = null;
        
        try {
            PreparedStatement selectPlayer = DBConnection.getInstance().prepareStatement("SELECT * FROM player WHERE user_name = ?");
            selectPlayer.setString(1, userName);
            ResultSet resultSet = selectPlayer.executeQuery();
            if (resultSet.next() && password.equals(resultSet.getString(Tables.player.PASSWORD))) {
                int id = resultSet.getInt(Tables.player.ID);
                int score = resultSet.getInt(Tables.player.SCORE);
                String fName = resultSet.getString(Tables.player.FIRST_NAME);
                String lName = resultSet.getString(Tables.player.LAST_NAME);
                String imageURL = resultSet.getString(Tables.player.IMAGE_URL);
                player = new Player(id, fName, lName, userName, imageURL, score);
                player.setStatus(Constant.ONLINE_STATUS);

                int isUpdated = updatePlayerStatus(json, Constant.ONLINE_STATUS);

                if (isUpdated == 1) {
                    res.put(Constant.STATUS_CODE_KEY, Constant.STATUS_CODE_SUCCESSED);
                    res.put(Constant.PLAYER_DATA_KEY, player);
                } else {
                    res.put(Constant.STATUS_CODE_KEY, Constant.STATUS_CODE_FAILED);
                }

            } else {
                res.put(Constant.STATUS_CODE_KEY, Constant.STATUS_CODE_FAILED);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Utils.toJson(res);
    }

    public static void updatePlayerScore(int id) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().prepareStatement("UPDATE player SET " + Tables.player.SCORE + " = " + Tables.player.SCORE + " + 1 WHERE " + Tables.player.ID + " = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public static void saveGame(int host_id, int guest_id, String board) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().
                prepareStatement("insert into saved_game (" + Tables.saved_game.HOST_ID + ", " + Tables.saved_game.GUEST_ID
                        + "," + Tables.saved_game.GAME_BOARD + ") VALUES ( ? , ? , ? )");
        preparedStatement.setInt(1, host_id);
        preparedStatement.setInt(2, guest_id);
        preparedStatement.setString(3, board);
        preparedStatement.executeUpdate();
    }

    public static void changeStatus(int id, int status) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().prepareStatement("UPDATE player SET " + Tables.player.STATUE + " = " + Tables.player.SCORE + " = ?  WHERE " + Tables.player.ID + " = ?");
        preparedStatement.setInt(1, status);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    public static JsonObject getSavedGamesFromDataBase(String logedUserId) {
        HashMap<String, Object> gamesJson = new HashMap<>();
        Game game = null;
        try {
            PreparedStatement savedGames = DBConnection.getInstance().prepareStatement("SELECT * FROM saved_game WHERE host_id = ? OR guest_id = ? ");
            savedGames.setString(1, logedUserId);
            savedGames.setString(2, logedUserId);

            ResultSet resultSet = savedGames.executeQuery();

            if (resultSet.next()) {
                int gameId = resultSet.getInt(Tables.saved_game.ID);
                int hostID = resultSet.getInt(Tables.saved_game.HOST_ID);
                int guestID = resultSet.getInt(Tables.saved_game.GUEST_ID);
                String gameBoard = resultSet.getString(Tables.saved_game.GAME_BOARD);
                
               game = new Game(gameId , hostID, guestID, gameBoard);
               gamesJson.put(Constant.GAME_DATA_KEY, game);
                

            } else {
                System.err.println("No Saved Games");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Utils.toJson(gamesJson);
    }
}





