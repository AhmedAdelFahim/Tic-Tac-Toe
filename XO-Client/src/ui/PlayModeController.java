/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.stage.Stage;
import model.ClientSideHandler;
import model.Player;
import viewmodel.PlayModeViewModel;

/**
 *
 * @author eg
 */
public class PlayModeController implements Initializable {

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
        Online.setCellValueFactory(new PropertyValueFactory("userName"));
        Ranks.setCellValueFactory(new PropertyValueFactory("score"));
        playerTable.setItems(PlayModeViewModel.getOnlinePlayers());

        playerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                System.out.println(((Player)newValue).getUserName());

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
    }
}
