package ui;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Game;

public class GameCellFactory implements Callback<ListView<Game>, ListCell<Game>> {
    @Override
    public ListCell<Game> call(ListView<Game> param) {
        return new SavedGameItem();
    }
}


