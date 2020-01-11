/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.Server;

/**
 *
 * @author migo2
 */
public class AdminLoginController implements Initializable {

    @FXML
    private Button  Login;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                    System.out.println("congratulations");

    }
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {
//                    System.out.println("congratulations");

        if (username.getText().equals("admin") && password.getText().equals("admin")) {
            System.out.println("congratulations");
            loadAdminDashboard();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("Wrong Credentials.");
                alert.show();
        }

    }

    public void loadAdminDashboard() throws IOException{
                FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
                Parent root;
                root = (Parent) fxmlLoader.load();
                Scene sceneDashboard = new Scene(root,800,500);
                Stage stage = (Stage) Login.getScene().getWindow();
                stage.setScene(sceneDashboard);
                stage.show();
    }

}
