/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.ClientSideHandler;
import utils.Constant;
import viewmodel.LogoutViewModel;

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

    private ChangeListener logoutListener;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Font.loadFont(getClass().getResource("/font/Bangers.ttf").toExternalForm(),28);
        addListeners();
    }

    private void addListeners(){
        logoutListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println(oldValue+"   "+newValue);
                if (newValue && !oldValue)  {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("open.fxml"));
                            try {
                                Parent root = fxmlLoader.load();
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) logoutButton.getScene().getWindow();
                                stage.setScene(scene);
                                stage.setTitle("Tic Tac Toe");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                } else if(!newValue && !oldValue)
                {
                }
                LogoutViewModel.toSignUpFlagProperty().removeListener(this);
                LogoutViewModel.setToSignUpFlag(false);
            }
        };
        LogoutViewModel.toSignUpFlagProperty().addListener(logoutListener);
    }
    private void removeListeners(){
        LogoutViewModel.toSignUpFlagProperty().removeListener(logoutListener);
    }

    @FXML
    private void handleEasyAction(ActionEvent event) {
        PlayScreenView.setLevel(1);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayScreen.fxml"));
        try {
            removeListeners();
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) hardButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Tic Tac Toe");
        } catch (IOException ex) {
            Logger.getLogger(LevelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleMediumAction(ActionEvent event) {
        PlayScreenView.setLevel(500);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayScreen.fxml"));
        try {
            removeListeners();
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) hardButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Tic Tac Toe");
        } catch (IOException ex) {
            Logger.getLogger(LevelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleHardButton(ActionEvent event) {

        PlayScreenView.setLevel(Double.POSITIVE_INFINITY);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayScreen.fxml"));
        try {
            removeListeners();
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) hardButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Tic Tac Toe");
        } catch (IOException ex) {
            Logger.getLogger(LevelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLogoutButton(ActionEvent event) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.USER_NAME_KEY, ClientSideHandler.getInstance().getCurrentPlayer().getUserName());
        map.put(Constant.REQUEST_TYPE, Constant.LOGOUT);
        LogoutViewModel.logout(map);
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            removeListeners();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayMode.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene sceneDashboard = new Scene(root);
            Stage stage = (Stage) easyButton.getScene().getWindow();
            stage.setScene(sceneDashboard);
            stage.setTitle("Tic Tac Toe");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LevelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
