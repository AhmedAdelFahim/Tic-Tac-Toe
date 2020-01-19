package ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import model.ClientSideHandler;
import model.Game;
import model.Player;
import utils.Constant;
import viewmodel.PlayModeViewModel;

import java.io.IOException;
import java.util.HashMap;

public class SavedGameItem extends ListCell<Game> {

    @FXML
    private Label hName;

    @FXML
    private Label gName;

    @FXML
    private Button continueGame;



    public SavedGameItem() {
        loadFXML();
    }

  

    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../res/layout/saved_games_item.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Game item, boolean empty) {
        super.updateItem(item, empty);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(empty) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
                else {

                   //hName.setText(item.get);
                   //gName.setText(item.get);
                    continueGame.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("AAA");
                        }
                    });
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            }
        });

    }
}
