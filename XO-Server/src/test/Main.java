package test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import db.DBQueries;

import java.util.HashMap;

public class Main {

    public static void main(String [] args){
        HashMap<String,Object> map = new HashMap<>();

        map.put("first_name","Ahmed");
        map.put("last_name","Adel");
        map.put("user_name","ahmed_adel");
        map.put("password","1234");
//        JsonObject jsonObject = new JsonParser().parse(DBQueries.signUp(map.toString())).getAsJsonObject().toString();
        System.out.println(new JsonParser().parse(DBQueries.signUp(map.toString())).getAsJsonObject().toString());
    }
}
