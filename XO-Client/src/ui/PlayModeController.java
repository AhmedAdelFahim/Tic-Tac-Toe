/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author eg
 */
public class PlayModeController implements Initializable {

    @FXML
    private ImageView computer;
    @FXML
    private TableView<?> tableOnlinePlayer;
    @FXML
    private TableColumn<?, ?> online;
    @FXML
    private TableColumn<?, ?> ranks;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //table elements 
    }

    @FXML
    private void handleComputerButton(MouseEvent event) {
        System.out.println("clicked");
        //go to playgame with computer and one player
    }


}
