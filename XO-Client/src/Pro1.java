/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author eg
 */
public class Pro1 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

       
//        root.setStyle("-fx-fill: activeborder;-fx-border:green;-fx-font:15px sans-serif;-fx-background-color: darkslategrey;");

        Scene scene = new Scene(root,400,500);

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

//    private GridPane createRegistrationFormPane() {
//        // Instantiate a new Grid Pane
//        GridPane gridPane = new GridPane();
//
//        // Position the pane at the center of the screen, both vertically and horizontally
//        gridPane.setAlignment(Pos.CENTER);
//
//        // Set a padding of 20px on each side
//        gridPane.setPadding(new Insets(40, 40, 40, 40));
//
//        // Set the horizontal gap between columns
//        gridPane.setHgap(10);
//
//        // Set the vertical gap between rows
//        gridPane.setVgap(10);
//
//        // Add Column Constraints
//        // columnOneConstraints will be applied to all the nodes placed in column one.
//        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
//        columnOneConstraints.setHalignment(HPos.RIGHT);
//
//        // columnTwoConstraints will be applied to all the nodes placed in column two.
//        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
//        columnTwoConstrains.setHgrow(Priority.ALWAYS);
//
//        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
//
//        return gridPane;
//    }
}
