/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import login.Test;

/**
 *
 * @author islam salah
 */
public class FXMLDocumentController implements Initializable {
    ClientHndeller clientHandeller = new ClientHndeller();

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LogInViewModel.toPlayScreenFlagProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayMode.fxml"));
                try {
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root,800,500);
                    Stage stage = (Stage) firstName.getScene().getWindow();
                    stage.setScene(scene);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

                System.out.println("FFF");
            }
        });

    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            System.out.println("Go To SignUp Page..");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/homeplay/PlayScreen.fxml"));
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            System.out.println("Logged In Successfully");
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLogIn(ActionEvent event) {
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
               map.put(Constant.REQUEST_TYPE,Constant.LOGIN);
                map.put(Constant.USER_NAME_KEY,userName.getText());
                map.put(Constant.PASSWORD_KEY,password.getText());
                LogInViewModel.logIn(map);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
