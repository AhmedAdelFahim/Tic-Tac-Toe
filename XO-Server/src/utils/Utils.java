package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;

import java.util.HashMap;
import model.Game;

public class Utils {
    public static String toString(HashMap<String,Object> map){
        return map.toString();
    }

    public static JsonObject toJson(String json){
        return new JsonParser().parse(json).getAsJsonObject();
    }

    public static JsonObject toJson(HashMap<String,Object> map){
        return new JsonParser().parse(toString(map)).getAsJsonObject();
    }
  
}
