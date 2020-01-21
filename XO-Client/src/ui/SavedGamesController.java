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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.ClientSideHandler;
import model.Game;
import utils.Constant;
import viewmodel.InvitationViewModel;
import viewmodel.LogoutViewModel;
import viewmodel.PlayModeViewModel;
import viewmodel.SavedGamesViewModel;

/**
 * FXML Controller class
 *
 * @author eg
 */
public class SavedGamesController implements Initializable {

//    @FXML
//    private ListView<?> listPlayer;
    @FXML
    private Button backButtton;
    @FXML
    private Button logoutbutton;
    @FXML
    private ListView<Game> savedGamesList;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       savedGamesList.setCellFactory(new GameCellFactory());
       savedGamesList.setItems(SavedGamesViewModel.getSavedGames());
    }    


    @FXML
    private void handleBackAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayMode.fxml"));
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML.fxml"));

            Parent root = (Parent) fxmlLoader.load();
            Scene sceneDashboard = new Scene(root);
            Stage stage = (Stage) logoutbutton.getScene().getWindow();
            stage.setScene(sceneDashboard);
            stage.setTitle("Tic Tac Toe");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LevelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLogoutAction(ActionEvent event) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.USER_NAME_KEY, ClientSideHandler.getInstance().getCurrentPlayer().getUserName());
        map.put(Constant.REQUEST_TYPE, Constant.LOGOUT);
        LogoutViewModel.logout(map); 
    }






}
