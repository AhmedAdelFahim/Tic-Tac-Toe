/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.ClientSideHandler;
import model.Player;
import utils.Constant;
import viewmodel.InvitationViewModel;
import viewmodel.LogInViewModel;
import viewmodel.PlayModeViewModel;
import viewmodel.SignUpViewModel;

/**
 *
 * @author eg
 */
public class PlayModeController implements Initializable {

    Thread playersThread;
    public Player currentPlayer;
    public static String senderUserName;
    public static JsonObject invitationJason;

    @FXML
    private ImageView computer;
//    @FXML
//    private TableView onlinePlayerTable;
//    @FXML
//    private TableColumn playerNameCol;
//    @FXML
//    private TableColumn playerScoreCol;

    @FXML
    private Button backButton;
    @FXML
    private Button handleLogoutAction;
    @FXML
    private Label user;
    @FXML
    private TableView playerTable;
    @FXML
    private TableColumn Online;
    @FXML
    private TableColumn Ranks;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentPlayer = ClientSideHandler.getInstance().getCurrentPlayer();
        Online.setCellValueFactory(new PropertyValueFactory("userName"));
        Ranks.setCellValueFactory(new PropertyValueFactory("score"));
        playerTable.setItems(PlayModeViewModel.getOnlinePlayers());

        playerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                //get  id of logged in user
                int invitedPlayerId = ((Player) newValue).getId();
                int senderPlayerId = currentPlayer.getId();

                String senderPlayerUserName = currentPlayer.getUserName();

                System.out.println(invitedPlayerId);
                System.out.println(senderPlayerId);

                HashMap<String, Object> map = new HashMap<>();
                map.put(Constant.REQUEST_TYPE, Constant.INVITE);
                map.put(Constant.SENDER_ID_KEY, senderPlayerId);
                map.put(Constant.RECIEVER_ID_KEY, invitedPlayerId);
                map.put(Constant.RECIEVER_NAME_KEY, ((Player) newValue).getUserName());
                map.put(Constant.SENDER_NAME_KEY, currentPlayer.getUserName());
                PlayModeViewModel.sendInvitation(map);
                // Alert Waiting for otherplayer
                // System.out.println("********Before  Invetation Recieved *********");

            }
        });
        System.out.println("******** BEFOR Invetation Recieved *********");

//        InvitationViewModel.tocurrentInviteScreenflagProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                System.out.println("******** Invetation Recieved *********");
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        ButtonType accept = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
//                        ButtonType decline = new ButtonType("Decline", ButtonBar.ButtonData.CANCEL_CLOSE);
//                        Alert alert = new Alert(AlertType.WARNING,
//                                "player" + senderUserName + "want to play", accept, decline);
//                        alert.setTitle("Invitation");
//                        alert.setHeaderText(null);
//                        alert.showAndWait();
//                        alert.getResult();
//                        if (alert.getResult() == accept) {//accept request
//                            System.err.println("Accepted Invivtation");
//                            acceptInvitation(invitationJason);
//                             System.err.println("After Accepted ");
//                            //do stuff//load the game
//                        }
//                        if (alert.getResult() == decline) {//decline request
//                            System.err.println("Decline Invivtation");
//                            declineInvitation(invitationJason);
//                            System.err.println("After Decline ");
//                        }
//
//                    }
//                });
//            } else {
//                System.out.println("FFF");
//            }
//        });
           InvitationViewModel.tocurrentInviteScreenflagProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("******** Invetation Recieved *********");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ButtonType accept = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
                        ButtonType decline = new ButtonType("Decline", ButtonBar.ButtonData.CANCEL_CLOSE);
                        Alert alert = new Alert(AlertType.WARNING,
                                "player" + senderUserName + "want to play", accept, decline);
                        alert.setTitle("Invitation");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        alert.getResult();
                        if (alert.getResult() == accept) {//accept request
                            System.err.println("Accepted Invivtation");
                            acceptInvitation(invitationJason);
                             System.err.println("After Accepted ");
                            //do stuff//load the game
                        }
                        if (alert.getResult() == decline) {//decline request
                            System.err.println("Decline Invivtation");
                            declineInvitation(invitationJason);
                            System.err.println("After Decline ");
                        }

                    }
                });
            } else {
                System.out.println("FFF");
            }
        });
    }

    @FXML
    private void handleComputerButton(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Level.fxml"));
        PlayScreenView.setMode(PlayScreenView.Mode.AI);
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) playerTable.getScene().getWindow();
            stage.setTitle("Select Level Tic Tac Toe");
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUpView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene sceneDashboard = new Scene(root);
            Stage stage = (Stage) computer.getScene().getWindow();
            stage.setScene(sceneDashboard);
            stage.setTitle("Select Play Mode Tic Tac Toe");
            stage.show();
        } catch (IOException ex) {
            /// Logger.getLogger(pro1.PlayModeController.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println("Dending Invetstion..");

    }

}
