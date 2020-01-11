package viewmodel;

import com.google.gson.JsonArray;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Player;

public class PlayModeViewModel {
    private static ObservableList<Player> onlinePlayers ;

    public static ObservableList<Player> getOnlinePlayers() {
        onlinePlayers = FXCollections.observableArrayList();
       //onlinePlayers.add(new Player("aaa",12));
        return onlinePlayers;
    }

    public static void addOnlinePlayer(JsonArray onlinePlayersJson){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                onlinePlayers.clear();
                for (int i = 0; i < onlinePlayersJson.size(); ++i) {
                    onlinePlayers.add(Player.preparePlayerData(onlinePlayersJson.get(i).getAsJsonObject()));

                }

            }
        });

    }
}
