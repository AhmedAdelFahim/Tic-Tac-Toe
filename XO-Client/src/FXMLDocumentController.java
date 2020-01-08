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

/**
 *
 * @author eg
 */
public class FXMLDocumentController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void connectClient(JsonObject jsonObject) {
        try {
            Socket socket = new Socket("127.0.0.1", 5200);
            dataInputStream = new DataInputStream(socket.getInputStream());
            printStream = new PrintStream(socket.getOutputStream());
            printStream.println(jsonObject);
            System.out.println(dataInputStream.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleRegisterButton0Action(ActionEvent event) {
        if ((firstName.getText().isEmpty())
                || (lastName.getText().isEmpty())
                || (userName.getText().isEmpty())
                || (password.getText().isEmpty())
                || (confirmPassword.getText().isEmpty())) {
            message.setText("Input is required");
        } else {

////            //fetch
            HashMap<String, Object> map = new HashMap<>();
//            map.put(Constant.REQUEST_TYPE, Constant.SIGN_UP);
//            map.put("first_name", firstName.getText());
//            map.put("last_name", lastName.getText());
//            map.put("username", userName.getText());
//            map.put("password", password.getText());
//            System.out.println(map);
            JsonObject jsonsend = new JsonObject();
            jsonsend.addProperty("request_Type", "2");
            jsonsend.addProperty("firstname", firstName.getText());
            jsonsend.addProperty("lasttname", lastName.getText());
            jsonsend.addProperty("username", userName.getText());
            jsonsend.addProperty("password", password.getText());
            map.put("userdata", jsonsend);
            System.out.println(map);
            message.setText("");
//call function to open connection
            connectClient(jsonsend);
////            //goto play mode or login page
////    
        }
        System.out.println("You clicked register!");

    }

    @FXML
    private void handlehaveAccountButtonAction(ActionEvent event) {
        System.out.println("You clicked haveaccount!");
        message.setText("");
        //go to login page

    }

}
