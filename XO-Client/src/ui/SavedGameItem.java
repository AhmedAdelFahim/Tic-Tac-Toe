package ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import model.ClientSideHandler;
import model.Game;
import model.Player;
import utils.Constant;
import viewmodel.PlayModeViewModel;

import java.io.IOException;
import java.util.HashMap;
import viewmodel.LogInViewModel;
import viewmodel.SavedGamesViewModel;

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Game item, boolean empty) {
        super.updateItem(item, empty);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (empty) {
                    setText(null);
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                } else {

                    hName.setText(item.getUserName());
                    gName.setText("Computer");
                    continueGame.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            PlayScreenView.setModeToAI();
                            PlayScreenView.setNewGame(false);
                            System.out.println(item.getGameBoard());
                            String str = item.getGameBoard();
                            str = str.substring(1,str.length()-1);
                            deleteSavedGame(item.getId());

                            PlayScreenView.resumeGame(str);
                            PlayScreenView.setLevel(500);
                            System.out.println(PlayScreenView.board);
                            FXMLLoader fxmlLoader = new FXMLLoader(PlayModeController.class.getResource("PlayScreen.fxml"));
                            try {
                                Parent root = fxmlLoader.load();
                                Scene scene = new Scene(root, 800, 500);
                                Stage stage = (Stage) hName.getScene().getWindow();
                                stage.setScene(scene);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }
                    });
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            }
        });

    }


    void deleteSavedGame(int gameId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.REQUEST_TYPE, Constant.DELETE_GAME);
        map.put(Constant.GAME_ID, gameId);
        SavedGamesViewModel.deleteSavedGame(map);
    }
}
