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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author eg
 */
public class LevelController implements Initializable {

    @FXML
    private Button easyButton;
    @FXML
    private Button mediumButton;
    @FXML
    private Button hardButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button backButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleEasyAction(ActionEvent event) {
    }

    @FXML
    private void handleMediumAction(ActionEvent event) {
    }

    @FXML
    private void handleHardButton(ActionEvent event) {
    }

    @FXML
    private void handleLogoutButton(ActionEvent event) {
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayMode.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene sceneDashboard = new Scene(root);
            Stage stage = (Stage) easyButton.getScene().getWindow();
            stage.setScene(sceneDashboard);
            stage.setTitle("Select Play Mode Tic Tac Toe");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LevelController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
}