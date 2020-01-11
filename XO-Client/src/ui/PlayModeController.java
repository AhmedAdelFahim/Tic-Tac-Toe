/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pro1.SignUpView.*;
/**
 *
 * @author eg
 */
public class PlayModeController implements Initializable {

    @FXML
    private ImageView computer;
    @FXML
    private Button backButton;
    @FXML
    private Label user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//      user.setText(welcome);
      
        //table elements 
    }

    @FXML
    private void handleComputerButton(MouseEvent event) {
        try {
            System.out.println("clicked");
            //go to playgame with computer and one player
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Level.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene sceneDashboard = new Scene(root);
            Stage stage = (Stage) computer.getScene().getWindow();
            stage.setScene(sceneDashboard);
            stage.setTitle("Select Level Tic Tac Toe");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(PlayModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLogoutAction(ActionEvent event) {
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
            Logger.getLogger(PlayModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    


}
