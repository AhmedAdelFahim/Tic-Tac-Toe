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
import model.Player;
import utils.Constant;
import viewmodel.PlayModeViewModel;

import java.io.IOException;
import java.util.HashMap;

public class SavedGameItem extends ListCell<Player> {

    @FXML
    private Label userName;

    @FXML
    private Label score;

    @FXML
    private Button invite;



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
    protected void updateItem(Player item, boolean empty) {
        super.updateItem(item, empty);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(empty) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
                else {
                    userName.setText(item.getUserName());
                    score.setText(Integer.toString(item.getScore()));
                    invite.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("AAA");
                            //get  id of logged in user
                            int invitedPlayerId = item.getId();
                            int senderPlayerId = ClientSideHandler.getInstance().getCurrentPlayer().getId();

                            String senderPlayerUserName = ClientSideHandler.getInstance().getCurrentPlayer().getUserName();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put(Constant.REQUEST_TYPE, Constant.INVITE);
                            map.put(Constant.SENDER_ID_KEY, senderPlayerId);
                            map.put(Constant.RECIEVER_ID_KEY, invitedPlayerId);
                            map.put(Constant.RECIEVER_NAME_KEY, item.getUserName());
                            map.put(Constant.SENDER_NAME_KEY, ClientSideHandler.getInstance().getCurrentPlayer().getUserName());
                            PlayModeViewModel.sendInvitation(map);
                        }
                    });
                    if(item.getStatus()== Constant.ONLINE_STATUS){
                        invite.setStyle("-fx-background-color: #9ccc65");
                        invite.setText("INVITE");
                        invite.setDisable(false);
                    } else if (item.getStatus()== Constant.BUSY_STATUS){
                        invite.setStyle("-fx-background-color: #ffb74d");
                        invite.setText("Playing...");
                        invite.setDisable(true);

                    }
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            }
        });

    }
}
