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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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

    @FXML
    private Label noOnlinePlayers;

    private ChangeListener logoutListener;
    private ChangeListener declinedInvitationListener;
    private ChangeListener currentInvitationListener;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Font.loadFont(getClass().getResource("../res/font/Bangers.ttf").toExternalForm(), 28);
        currentPlayer = ClientSideHandler.getInstance().getCurrentPlayer();
        onlineList.setCellFactory(new PlayerCellFactory());
        onlineList.setItems(PlayModeViewModel.getOnlinePlayers());
        onlineList.getItems().addListener(new ListChangeListener<Player>() {
            @Override
            public void onChanged(Change<? extends Player> c) {
                if (c.getList().size() > 0) {
                    noOnlinePlayers.setVisible(false);
                } else if (c.getList().size() == 0) {
                    noOnlinePlayers.setVisible(true);
                }
            }
        });
        PlayModeViewModel.getOnlinePlayersRequest();

        addListeners();
    }

    private void addListeners() {
        
        declinedInvitationListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
                if (newValue.intValue() == 1) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);//he must reply first
                        VBox dialogVbox = new VBox(20);
                        dialogVbox.getChildren().add(new Text("player " + OtherPlayer + " declined your invitation to play"));
                        HBox hbButtons = new HBox();
                        Button accept = new Button();
                        accept.setText("ok");
                        accept.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                dialog.close();

                               InvitationViewModel.resetDeclinedInvitationFlag();
                               InvitationViewModel.resetCurrentInviteScreenflag();

                            }
                        });

                        hbButtons.getChildren().add(accept);
                        hbButtons.setAlignment(Pos.CENTER_RIGHT);
                        BorderPane root = new BorderPane();
                        root.setPadding(new Insets(10)); // space between elements and window border
                        root.setCenter(dialogVbox);
                        root.setBottom(hbButtons);
                        Scene dialogScene = new Scene(root, 300, 100);
                        dialog.setTitle("Invitation Declined");
                        dialog.setScene(dialogScene);
                        dialog.show();
                    }
                });
            } else {
                System.out.println("FFF");
            }
            }};
        currentInvitationListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue){
                System.out.println(oldValue+" AAA "+newValue);
                if (newValue.intValue() == 1) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        System.out.println(oldValue+" BBB "+newValue);
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);//he must reply first
                        VBox dialogVbox = new VBox(20);
                        dialogVbox.getChildren().add(new Text("player " + OtherPlayer + " want to play with you"));
                        HBox hbButtons = new HBox();

                        Button accept = new Button();
                        accept.setText("Accept");
                        accept.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                acceptInvitation(invitationJason);
                                PlayScreenView.setModeToPlayers();
                                PlayScreenView.setToGuest();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayScreen.fxml"));
                                try {
                                    removeListeners();
                                    Parent root = fxmlLoader.load();
                                    Scene scene = new Scene(root);
                                    Stage stage = (Stage) computer.getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.setTitle("Tic Tac Toe");
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                InvitationViewModel.resetDeclinedInvitationFlag();
                                InvitationViewModel.resetCurrentInviteScreenflag();

                                dialog.close();
                            }
                        });
                        hbButtons.getChildren().add(accept);
                        hbButtons.setAlignment(Pos.CENTER_RIGHT);

                        Button decline = new Button();
                        decline.setText("Decline");
                        decline.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent event) {
                                System.out.println("Scrape button pressed.");
                                declineInvitation(invitationJason);
                                dialog.close();
                                InvitationViewModel.resetDeclinedInvitationFlag();
                                InvitationViewModel.resetCurrentInviteScreenflag();
                            }
                        });
                        hbButtons.getChildren().add(decline);
                        hbButtons.setAlignment(Pos.CENTER_RIGHT);

                        BorderPane root = new BorderPane();
                        root.setPadding(new Insets(10)); // space between elements and window border
                        root.setCenter(dialogVbox);
                        root.setBottom(hbButtons);
                        Scene dialogScene = new Scene(root, 300, 100);
                        dialog.setTitle("Invitation");
                        dialog.setScene(dialogScene);
                        dialog.show();
                    }
                });
            } else {
                System.out.println("FFF");
            }
            }};
        logoutListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println(oldValue + "   " + newValue);
                if (newValue && !oldValue) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("open.fxml"));
                            try {
                                Parent root = fxmlLoader.load();
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) onlineList.getScene().getWindow();
                                stage.setScene(scene);
                                stage.setTitle("Tic Tac Toe");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else if (!newValue && !oldValue) {
                }
                LogoutViewModel.toSignUpFlagProperty().removeListener(this);
                LogoutViewModel.setToSignUpFlag(false);
            }
        };
        LogoutViewModel.toSignUpFlagProperty().addListener(logoutListener);
        InvitationViewModel.toDeclinedInvitationFlag().addListener(declinedInvitationListener);
        InvitationViewModel.tocurrentInviteScreenflagProperty().addListener(currentInvitationListener);
    }

    private void removeListeners() {
        LogoutViewModel.toSignUpFlagProperty().removeListener(logoutListener);
        InvitationViewModel.toDeclinedInvitationFlag().removeListener(declinedInvitationListener);
        InvitationViewModel.tocurrentInviteScreenflagProperty().removeListener(currentInvitationListener);
    }

    @FXML
    private void handleComputerButton(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Level.fxml"));
        PlayScreenView.setModeToAI();
        PlayScreenView.setNewGame(true);
        try {
            removeListeners();
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) computer.getScene().getWindow();
            stage.setTitle("Tic Tac Toe");
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
        InvitationViewModel.resetCurrentInviteScreenflag();
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
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.USER_NAME_KEY, ClientSideHandler.getInstance().getCurrentPlayer().getUserName());
        map.put(Constant.REQUEST_TYPE, Constant.LOGOUT);
        LogoutViewModel.logout(map);
    }

    public void getSavedGames(ActionEvent actionEvent) { //sends a request to the server to the saved games

        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.SENDER_ID_KEY, ClientSideHandler.getInstance().getCurrentPlayer().getId());
        map.put(Constant.REQUEST_TYPE, Constant.SAVED_GAMES);
        SavedGamesViewModel.getSavedGames(map);
    }

    @FXML
    private void handleSaveAction(ActionEvent event) {
        try {
            getSavedGames(event);
            removeListeners();
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
