package viewmodel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ClientSideHandler;
import model.Game;
import model.Player;
import utils.Utils;

public class SavedGamesViewModel {
    private static ObservableList<Game> savedGames ;

  
     public static ObservableList<Game> getSavedGames() {
        savedGames = FXCollections.observableArrayList();
        return savedGames;
    }

    public static void savedGames(JsonArray savedGamesJson){
      Platform.runLater(new Runnable() {
            @Override
            public void run() {
                savedGames.clear();
//                System.err.println(savedGamesJson.get(0).getAsJsonObject());
                for (int i = 0; i < savedGamesJson.size(); ++i) {
                    Game game = Game.prepareGameData(savedGamesJson.get(i).getAsJsonObject());
                    savedGames.add(game);

                }
                System.err.println(savedGames);
            }
        });

    }
    
    
    

    
    public static boolean getSavedGames(HashMap map){
        ClientSideHandler.getInstance().handelSavedGames(Utils.toString(map));
        return true;
    }
   
}
