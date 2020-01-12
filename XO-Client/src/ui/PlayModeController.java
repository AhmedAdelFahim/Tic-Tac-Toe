/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayScreen.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root,800,500);
            Stage stage = (Stage) onlinePlayerTable.getScene().getWindow();
            stage.setScene(scene);


        } catch (IOException e) {
            e.printStackTrace();
        }
        //go
        System.out.println("clicked");
        //go to playgame with computer and one player
    }


}
