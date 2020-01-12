/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author eg
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SignUpView.fxml"));

       
//        root.setStyle("-fx-fill: activeborder;-fx-border:green;-fx-font:15px sans-serif;-fx-background-color: darkslategrey;");

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.setTitle("Registration Form Tic Tac Toe");

        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
