/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.MouseEvent;
import model.Player;
import viewmodel.PlayModeViewModel;

/**
 *
 * @author eg
 */
public class PlayModeController implements Initializable {

    @FXML
    private ImageView computer;
    @FXML
    private TableView onlinePlayerTable;
    @FXML
    private TableColumn playerNameCol;
    @FXML
    private TableColumn playerScoreCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerNameCol.setCellValueFactory(new PropertyValueFactory("userName"));
        playerScoreCol.setCellValueFactory(new PropertyValueFactory("score"));
        onlinePlayerTable.setItems(PlayModeViewModel.getOnlinePlayers());

        onlinePlayerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                System.out.println(((Player)newValue).getUserName());

            }
        });
    }

    @FXML
    private void handleComputerButton(MouseEvent event) {
        System.out.println("clicked");
        //go to playgame with computer and one player
    }


}
