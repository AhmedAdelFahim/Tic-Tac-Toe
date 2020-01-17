package ui;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Player;

public class PlayerCellFactory implements Callback<ListView<Player>, ListCell<Player>> {
    @Override
    public ListCell<Player> call(ListView<Player> param) {
        return new OnlinePlayerItem();
    }
}
