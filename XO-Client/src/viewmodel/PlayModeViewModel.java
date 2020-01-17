package viewmodel;

import com.google.gson.JsonArray;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ClientSideHandler;
import model.Player;
import ui.PlayScreenView;
import utils.Utils;

public class PlayModeViewModel {
    private static ObservableList<Player> onlinePlayers ;

    public static ObservableList<Player> getOnlinePlayers() {
        onlinePlayers = FXCollections.observableArrayList();
       onlinePlayers.add(new Player("aaa",12));
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
    
    public static boolean sendInvitation(HashMap map){
        ClientSideHandler.getInstance().handelInvitation(Utils.toString(map));
        return true;
    }
    public static boolean acceptInvitation(HashMap map){
        ClientSideHandler.getInstance().handelInvitation(Utils.toString(map));
        return true;
    }
   
    public static boolean declineInvitation(HashMap map){
        ClientSideHandler.getInstance().handelInvitation(Utils.toString(map));
        return true;
    }
}
