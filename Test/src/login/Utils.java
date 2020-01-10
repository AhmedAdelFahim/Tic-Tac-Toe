/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

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
