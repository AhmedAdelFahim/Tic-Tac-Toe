/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author eg
 */
public class OpenController implements Initializable {

    @FXML
    private Button playButton;
    @FXML
    private Button optionsButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label optionslabel;
    @FXML
    private Label optionslabel1;
    @FXML
    private ImageView image;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handlePlayAction(ActionEvent event) {
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUpView.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) optionslabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Tic Tac Toe");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    @FXML
    private void handleExitAction(ActionEvent event) {
         Platform.exit();
    }


    @FXML
    private void handleOptionsAction(ActionEvent event) {
     optionslabel.setText("Use Your Mouse To Click On ");
     optionslabel1.setText("The Board  At your Turn");
    }

    @FXML
    private void handleErase(MouseEvent event) {
        optionslabel.setText("");
        optionslabel1.setText("");
    }
    
    
}
