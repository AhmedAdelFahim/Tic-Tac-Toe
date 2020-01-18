/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.ClientSideHandler;
import model.Player;
import utils.Constant;
import viewmodel.*;

/**
 *
 * @author eg
 */
public class PlayModeController implements Initializable {

    Thread playersThread;
    public static Player currentPlayer;
    public static String OtherPlayer;
    public static String OtherPlayerId;
    public static JsonObject invitationJason;

    @FXML
    private ImageView computer;

    
    @FXML
    private Button handleLogoutAction;
    @FXML
    private Label user;

    @FXML
    private ListView<Player> onlineList;

    /*@FXML
    private TableView playerTable;
    @FXML
    private TableColumn Online;
    @FXML
    private TableColumn Ranks;*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentPlayer = ClientSideHandler.getInstance().getCurrentPlayer();
        onlineList.setCellFactory(new PlayerCellFactory());
        onlineList.setItems(PlayModeViewModel.getOnlinePlayers());
//        listView.setItems(names);
        /*Online.setCellValueFactory(new PropertyValueFactory("userName"));
        Ranks.setCellValueFactory(new PropertyValueFactory("score"));
        playerTable.setItems(PlayModeViewModel.getOnlinePlayers());*/

       /* playerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                //get  id of logged in user
                int invitedPlayerId = ((Player) newValue).getId();
                int senderPlayerId = currentPlayer.getId();

                String senderPlayerUserName = currentPlayer.getUserName();
                HashMap<String, Object> map = new HashMap<>();
                map.put(Constant.REQUEST_TYPE, Constant.INVITE);
                map.put(Constant.SENDER_ID_KEY, senderPlayerId);
                map.put(Constant.RECIEVER_ID_KEY, invitedPlayerId);
                map.put(Constant.RECIEVER_NAME_KEY, ((Player) newValue).getUserName());
                map.put(Constant.SENDER_NAME_KEY, currentPlayer.getUserName());
                PlayModeViewModel.sendInvitation(map);

            }
        });*/
        InvitationViewModel.toDeclinedInvitationFlag().addListener((observable, declinedFlagOldValue, declinedFlagNewValue) -> {
            if (declinedFlagNewValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                                "player" + OtherPlayer + "declined your invitation to play");
                        alert.setTitle("Invitation Declined");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        alert.getResult();
                    }
                });
            } else {
                System.out.println("FFF");
            }
        });
           InvitationViewModel.tocurrentInviteScreenflagProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ButtonType accept = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                        ButtonType decline = new ButtonType("Decline", ButtonBar.ButtonData.CANCEL_CLOSE);
                        Alert alert = new Alert(Alert.AlertType.WARNING,
                                "player" + OtherPlayer + "want to play", accept, decline);
                        alert.setTitle("Invitation");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        alert.getResult();
                        if (alert.getResult() == accept) {//accept request
                            acceptInvitation(invitationJason);
                            PlayScreenView.setModeToPlayers();
                            System.out.println("the Other Player Is " + OtherPlayer);
                            System.out.println("the Other Player id  " + OtherPlayerId);
                            PlayScreenView.setToGuest();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayScreen.fxml"));
                            try {
                                Parent root = fxmlLoader.load();
                                Scene scene = new Scene(root,800,500);
                                Stage stage = (Stage) computer.getScene().getWindow();
                                stage.setScene(scene);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        if (alert.getResult() == decline) {//decline request
                            declineInvitation(invitationJason);
                        }

                    }
                });
            } else {
                System.out.println("FFF");
            }
        });

        LogoutViewModel.toSignUpFlagProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
                        try {
                            Parent root = fxmlLoader.load();
                            Scene scene = new Scene(root);
                            Stage stage = (Stage) computer.getScene().getWindow();
                            stage.setScene(scene);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }




    @FXML
    private void handleComputerButton(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Level.fxml"));
        PlayScreenView.setModeToAI();
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) computer.getScene().getWindow();
            stage.setTitle("Select Level Tic Tac Toe");
            stage.setScene(scene);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void acceptInvitation(JsonObject jsonInvitation) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.REQUEST_TYPE, Constant.ACCEPT_INVITATION);
        map.put(Constant.SENDER_ID_KEY, jsonInvitation.get(Constant.RECIEVER_ID_KEY));
        map.put(Constant.SENDER_NAME_KEY, jsonInvitation.get(Constant.RECIEVER_NAME_KEY));
        map.put(Constant.RECIEVER_ID_KEY, jsonInvitation.get(Constant.SENDER_ID_KEY));
        map.put(Constant.RECIEVER_NAME_KEY, jsonInvitation.get(Constant.SENDER_NAME_KEY));
        PlayModeViewModel.acceptInvitation(map);
    }

    void declineInvitation(JsonObject jsonInvitation) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.REQUEST_TYPE, Constant.DECLINE_INVITATION);
        map.put(Constant.SENDER_ID_KEY, jsonInvitation.get(Constant.RECIEVER_ID_KEY));
        map.put(Constant.SENDER_NAME_KEY, jsonInvitation.get(Constant.RECIEVER_NAME_KEY));
        map.put(Constant.RECIEVER_ID_KEY, jsonInvitation.get(Constant.SENDER_ID_KEY));
        map.put(Constant.RECIEVER_NAME_KEY, jsonInvitation.get(Constant.SENDER_NAME_KEY));
        PlayModeViewModel.declineInvitation(map);
        InvitationViewModel.resetCurrentInviteScreenflag();
    }

    
    
    public void handleLogoutAction(ActionEvent actionEvent) {

        HashMap<String,Object> map = new HashMap<>();
        map.put(Constant.USER_NAME_KEY,ClientSideHandler.getInstance().getCurrentPlayer().getUserName());
        map.put(Constant.REQUEST_TYPE,Constant.LOGOUT);
        LogoutViewModel.logout(map);
    }
    @FXML
    private void handleSaveAction(ActionEvent event) {
         try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("savedGames.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene sceneDashboard = new Scene(root);
            Stage stage = (Stage) computer.getScene().getWindow();
            stage.setScene(sceneDashboard);
            stage.setTitle("Select Saved Game Tic Tac Toe");
            stage.show();
        } catch (IOException ex) {
            //Logger.getLogger(PlayModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
