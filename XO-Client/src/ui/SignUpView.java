/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro1;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author eg
 */
public class SignUpView implements Initializable {

    public static DataInputStream dataInputStream;
    public static PrintStream printStream;
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
    private Label label1;
    @FXML
    private Label TicTacToe;

//    String welcome = new String(userName.getText());
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

//    public void connectClient(JsonObject jsonObject) {
//        try {
//            Socket socket = new Socket("127.0.0.1", 5200);
//            dataInputStream = new DataInputStream(socket.getInputStream());
//            printStream = new PrintStream(socket.getOutputStream());
//            printStream.println(jsonObject);
//            System.out.println(dataInputStream.readLine());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
    ;
//     public String sendUserName() {
//        return welcome;
//    }

    @FXML
    private void handleRegisterButton0Action(ActionEvent event) {
        System.out.println("You clicked register!");
        if ((firstName.getText().isEmpty())
                || (lastName.getText().isEmpty())
                || (userName.getText().isEmpty())
                || (password.getText().isEmpty())
                || (confirmPassword.getText().isEmpty())) {
            message.setText("Input is required");

        } 
        else if ((confirmPassword.getText().equals(password.getText()))
                && (firstName.getText().matches("(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,20}$"))
                && (lastName.getText().matches("(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,25}$"))
                && (userName.getText().matches("\\w+"))
                ) {
            try {
                ////            //fetch
                HashMap<String, Object> map = new HashMap<>();
//            map.put(Constant.REQUEST_TYPE, Constant.SIGN_UP);
//            map.put("first_name", firstName.getText());
//            map.put("last_name", lastName.getText());
//            map.put("username", userName.getText());
//            map.put("password", password.getText());
//            System.out.println(map);

                message.setText("");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayMode.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Scene sceneDashboard = new Scene(root);
                Stage stage = (Stage) firstName.getScene().getWindow();
                stage.setScene(sceneDashboard);
                stage.setTitle("Select Play Mode Tic Tac Toe");
                stage.show();

//              call function to open connection
//              connectClient(jsonsend);
////            //goto play mode or login page
////    
            } catch (IOException ex) {
                Logger.getLogger(SignUpView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//                else if(!(firstName.getText().matches("(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,20}$")))
//                {
//                     message.setText("Invalid firstname char small/capital");
//                }
//                else if(!(lastName.getText().matches("(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,25}$")))
//                        {
//                            message.setText("Invalid lastname char small/capital");
//                        }
//                else if(!(userName.getText().matches("\\w+"))) {
//                   
//                     message.setText("Invalid username user123");
//                     
//                }
        else {
            message.setText("Invalid input");
            System.out.println("You clicked register!");
        }
    }

    @FXML
    private void handlehaveAccountButtonAction(ActionEvent event) throws IOException {
//         System.out.println("Have Account");

        //go to login page
    }

}
