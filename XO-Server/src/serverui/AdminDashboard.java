/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author migo2
 */
public class AdminDashboard extends Application {
    
    @Override
    public void start(Stage stageDashboard) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
        
        Scene sceneDashboard = new Scene(root,800,500);
       
        
        stageDashboard.setScene(sceneDashboard);
        stageDashboard.setResizable(false);
        stageDashboard.setTitle("Tic Tac Toe");
        stageDashboard.show();
    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        launch(args);
//    }
    
}
