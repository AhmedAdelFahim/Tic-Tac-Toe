/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
import javafx.scene.control.Button;
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
import viewmodel.LogInViewModel;
import viewmodel.PlayModeViewModel;

/**
 *
 * @author eg
 */
public class PlayModeController implements Initializable {
    Thread playersThread;
    public Player currentPlayer;
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
                int invitedPlayerId = ((Player)newValue).getId();
                int snderPlayerId = currentPlayer.getId();
                
                System.out.println(invitedPlayerId);
                System.out.println(snderPlayerId);
                
                HashMap<String, Object> map = new HashMap<>();
                map.put(Constant.REQUEST_TYPE,Constant.INVITE);
                map.put(Constant.SENDER_ID_KEY,snderPlayerId);
                map.put(Constant.RECIEVER_ID_KEY,invitedPlayerId);
                map.put(Constant.USER_NAME_KEY,currentPlayer.getUserName());
                PlayModeViewModel.sendInvitation(map);
                // Alert Waiting for otherplayer
                

            }
        });
    }

    @FXML
    private void handleComputerButton(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Level.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) playerTable.getScene().getWindow();
            stage.setTitle("Select Level Tic Tac Toe");
            stage.setScene(scene);


        } catch (IOException e) {
            e.printStackTrace();
        }
        //go
        System.out.println("clicked");
        //go to playgame with computer and one player
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


    public void handleLogoutAction(ActionEvent actionEvent) {
        System.out.println("Dending Invetstion..");

        
        
        
        
    }
    
    
    

}
