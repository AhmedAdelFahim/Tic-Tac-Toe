/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homeplay;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author islam salah
 */
public class PlayScreenFXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button pos_1;
    @FXML
    private Button pos_2;
    @FXML
    private Button pos_3;
    @FXML
    private Button pos_4;
    @FXML
    private Button pos_5;
    @FXML
    private Button pos_6;
    @FXML
    private Button pos_7;

    @FXML
    private Button pos_8;

    @FXML
    private Button pos_9;
    @FXML
    private FontAwesomeIconView gameStatusButton;
    @FXML
    private FontAwesomeIconView userPicture;
    @FXML
    private FontAwesomeIconView opponentPicture;
    @FXML
    private Label userName;
    @FXML
    private Label opponentUserName;
    @FXML
    private Label userCharacter;
    @FXML
    private Label opponentCharacter;
    @FXML
    private FontAwesomeIconView list;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Pso_1_Handeler(ActionEvent event) {
        pos_1.setText("O");
        pos_1.setDisable(true);
        System.out.println("Pos_1");

    }

    @FXML
    private void Pso_4_Handeler(ActionEvent event) {
        pos_4.setText("O");
        pos_4.setDisable(true);
        System.out.println("Pos_4");

    }

    @FXML
    private void Pso_2_Handeler(ActionEvent event) {
        pos_2.setText("X");
        pos_2.setDisable(true);
        System.out.println("Pos_2");

    }

    @FXML
    private void Pso_5_Handeler(ActionEvent event) {
        pos_5.setText("X");
        pos_5.setDisable(true);
        System.out.println("Pos_5");
    }

    @FXML
    private void Pso_3_Handeler(ActionEvent event) {
        pos_3.setText("X");
        pos_3.setDisable(true);
        System.out.println("Pos_3");
    }

    @FXML
    private void Pso_7_Handeler(ActionEvent event) {
        pos_7.setText("X");
        pos_7.setDisable(true);
        System.out.println("Pos_7");
    }

    @FXML
    private void Pso_8_Handeler(ActionEvent event) {
        pos_8.setText("X");
        pos_8.setDisable(true);
        System.out.println("Pos_8");
    }

    @FXML
    private void Pso_6_Handeler(ActionEvent event) {
        pos_6.setText("X");
        pos_6.setDisable(true);
        System.out.println("Pos_6");
    }

    @FXML
    private void Pso_9_Handeler(ActionEvent event) {
        pos_9.setText("X");
        pos_9.setDisable(true);
        System.out.println("Pos_9");
    }

    @FXML
    private void gameStatus(MouseEvent event) {
    }

}