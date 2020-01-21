/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.google.gson.JsonObject;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import utils.Constant;
import viewmodel.LogInViewModel;
import viewmodel.SignUpViewModel;

/**
 *
 * @author islam salah
 */
public class LoginView implements Initializable {

    String userName = new String("");
    String passWord = new String("");

    @FXML
    private Label headerLabel;
    @FXML
    private Label loginResult;
    @FXML
    private Button logInButton;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signUpButton;

    private ChangeListener loginListener;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Font.loadFont(getClass().getResource("/font/Bangers.ttf").toExternalForm(),28);
        //headerLabel.setStyle("-fx-font-family: Bangers");

        addListeners();

    }

    private void addListeners(){

        loginListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(oldValue +" "+newValue);
                if (newValue.intValue() == 1) {

                    try {
                        removeListener();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayMode.fxml"));
                        ///System.out.println(Thread.currentThread().getName());
                        Parent root = fxmlLoader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) logInButton.getScene().getWindow();

                        stage.setScene(scene);
                        stage.setTitle("Tic Tac Toe");
                        // LogInViewModel.toPlayScreenFlagProperty().removeListener();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if(newValue.intValue() == 0){

                    loginResult.setText("Login Failed");
                }
                LogInViewModel.setToPlayScreenFlag(-1);
                //LogInViewModel.toPlayScreenFlagProperty().removeListener(this);
            }
        };

        LogInViewModel.toPlayScreenFlagProperty().addListener(loginListener);


/*
        LogInViewModel.toPlayScreenFlagProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println(newValue);


        });*/
    }

    private void removeListener(){
        LogInViewModel.toPlayScreenFlagProperty().removeListener(loginListener);
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUpView.fxml"));
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Tic Tac Toe");
            removeListener();

        } catch (IOException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLogIn(ActionEvent event) {
        LogInViewModel.setToPlayScreenFlag(-1);
        int logInStatus = 1;
        userName = userNameTextField.getText();
        passWord = passwordField.getText();

        try {

            if (userName.isEmpty()) {
                loginResult.setText("User Name Required..");
                logInStatus = 0;
                return;
            }
            if (passWord.isEmpty()) {
                loginResult.setText("Password Required..");
                logInStatus = 0;
                return;
            }

            loginResult.setText("");

            if (logInStatus == 1) {
                HashMap<String, Object> map = new HashMap<>();
                map.put(Constant.REQUEST_TYPE, Constant.LOGIN);
                map.put(Constant.USER_NAME_KEY, userName);
                map.put(Constant.PASSWORD_KEY, passWord);
                System.out.println(map.toString());
                LogInViewModel.logIn(map);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
