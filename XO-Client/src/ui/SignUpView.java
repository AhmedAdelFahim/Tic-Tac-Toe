/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import utils.Constant;
import viewmodel.SignUpViewModel;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author eg
 */
public class SignUpView implements Initializable {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button registerButton;
    @FXML
    private Label message;
    @FXML
    private Button haveAccountButton;
    @FXML
    private Label TicTacToe;
    @FXML
    private Label label1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Font.loadFont(getClass().getResource("../res/font/Bangers.ttf").toExternalForm(),28);
        SignUpViewModel.toPlayScreenFlagProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayMode.fxml"));
                try {
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) firstName.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Tic Tac Toe");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

                System.out.println("FFF");
            }
        });
    }

    @FXML
    private void handleRegisterButton0Action(ActionEvent event) {
        if ((firstName.getText().isEmpty())
                || (lastName.getText().isEmpty())
                || (userName.getText().isEmpty())
                || (password.getText().isEmpty())
                || (confirmPassword.getText().isEmpty())) {
            message.setText("Input is required");
        } else if ((confirmPassword.getText().equals(password.getText()))
                && (firstName.getText().matches("(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,20}$"))
                && (lastName.getText().matches("(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,25}$"))
                && (userName.getText().matches("\\w+"))) {
            HashMap<String, Object> map = new HashMap<>();
            message.setText("");

            map.put(Constant.REQUEST_TYPE, Constant.SIGN_UP);
            map.put(Constant.USER_NAME_KEY, userName.getText());
            map.put(Constant.FIRST_NAME_KEY, firstName.getText());
            map.put(Constant.LAST_NAME_KEY, lastName.getText());
            map.put(Constant.PASSWORD_KEY, password.getText());
            SignUpViewModel.signUp(map);
        } else {
            message.setText("Invalid input");
            //System.out.println("You clicked register!");
        }

    }

    @FXML
    private void handlehaveAccountButtonAction(ActionEvent event) {
        //System.out.println("You clicked haveaccount!");
        message.setText("");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) firstName.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Tic Tac Toe");

        } catch (IOException e) {
            e.printStackTrace();
        }
        //go to login page

    }

}
