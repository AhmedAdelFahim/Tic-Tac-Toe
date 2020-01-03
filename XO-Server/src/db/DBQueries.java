package db;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Player;
import utils.Constant;
import utils.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
    public static JsonObject signUp(String json){
        HashMap<String,Object> res = new HashMap<>();
        int isInserted = 0;
        JsonObject jsonObject = Utils.toJson(json);
        String password = jsonObject.get("password").toString();
        String fName = jsonObject.get("first_name").toString();
        String lName = jsonObject.get("last_name").toString();
        String userName = jsonObject.get("user_name").toString();

        try {
            PreparedStatement signUpStatment = DBConnection.getInstance().prepareStatement("INSERT INTO player (first_name,last_name,password,user_name) VALUES (?,?,?,?)");
            signUpStatment.setString(1,fName);
            signUpStatment.setString(2,lName);
            signUpStatment.setString(3,password);
            signUpStatment.setString(4,userName);
            isInserted = signUpStatment.executeUpdate();
        } catch (SQLException e){
            //res.put("res_status",400);
            System.out.println(e.getMessage());
            e.printStackTrace();


        }

        if (isInserted == 1) {
            res.put(Constant.STATUS_CODE_KEY,Constant.STATUS_CODE_SUCCESSED);
            res.put("player_data",getPlayerByUserName(json));
        }
        else {
            res.put(Constant.STATUS_CODE_KEY,Constant.STATUS_CODE_FAILED);
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
    public static String getPlayerByUserName(String json){
        JsonObject jsonObject = Utils.toJson(json);
        String userName = jsonObject.get("user_name").toString();
        Player player = null;
        try {
            PreparedStatement selectPlayer = DBConnection.getInstance().prepareStatement("SELECT * FROM player WHERE user_name = ?");
            selectPlayer.setString(1,userName);
            ResultSet resultSet = selectPlayer.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(Tables.player.ID);
            int score = resultSet.getInt(Tables.player.SCORE);
            String fName = resultSet.getString(Tables.player.FIRST_NAME);
            String lName = resultSet.getString(Tables.player.LAST_NAME);
            String imageURL = resultSet.getString(Tables.player.IMAGE_URL);
            player = new Player(id,fName,lName,userName,imageURL,score);
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

    public static JsonObject logout(String json){
        HashMap<String,Object> res = new HashMap<>();
        try {
            JsonObject jsonObject = Utils.toJson(json);
            PreparedStatement preparedStatement = DBConnection.getInstance().prepareStatement("UPDATE player SET "+Tables.player.STATUE+" = ? WHERE "+Tables.player.USER_NAME +" = ?");
            preparedStatement.setInt(1,Constant.OFFLINE_STATUS);
            preparedStatement.setString(2,jsonObject.get(Tables.player.USER_NAME).toString());
            int isUpdated = preparedStatement.executeUpdate();

            if(isUpdated == 1){
                res.put(Constant.STATUS_CODE_KEY,Constant.STATUS_CODE_SUCCESSED);
            } else {
                res.put(Constant.STATUS_CODE_KEY,Constant.STATUS_CODE_FAILED);
            }

        } catch (SQLException e) {
            res.put(Constant.STATUS_CODE_KEY,Constant.STATUS_CODE_FAILED);
            e.printStackTrace();
        }
        return Utils.toJson(res);
    }

}
