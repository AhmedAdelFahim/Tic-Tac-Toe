/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.DelayQueue;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Player;
import server.PlayerHandler;
import server.Server;

/**
 *
 * @author migo2
 */
public class AdminDashboardController implements Initializable {

    Server server;
//    String[] onlineTest = {"hello", "hi", "world"};
    Thread playersThread;

    @FXML
    private Label serverMessage;
    @FXML
    private Button TurnOffServer;
    @FXML
    private Button TurnOnServer;

    @FXML
    private FlowPane flowpaneLeft;
    @FXML
    private FlowPane flowpaneRight;

    Thread serverThread = new Thread(new Runnable() {
        @Override
        public void run() {
            server = new Server();
            server.startServer();
        }
    });

    @FXML
    private void startConnection(ActionEvent event) {
        
        TurnOffServer.setDisable(false);
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                server = new Server();
                server.startServer();
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
        updateOnline();
        serverMessage.setText("  Server is Running");
        serverMessage.setStyle("-fx-background-color: lightgreen;");
        
        TurnOnServer.setDisable(true);
    }

    @FXML
    private void stopConnection(ActionEvent event) {
        TurnOffServer.setDisable(true);
        try {
            server.closeServer();
        } catch (IOException ex) {
            Logger.getLogger(AdminDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        flowpaneLeft.getChildren().clear();
        flowpaneRight.getChildren().clear();
        serverThread.stop();
        playersThread.stop();
        serverMessage.setText("  Server is Stopped");
        serverMessage.setStyle("-fx-background-color: lightcoral;");
        TurnOnServer.setDisable(false);

    }

    @FXML
    private void onlinePlayers(ActionEvent event) {
        System.out.println("You clicked me!");
    }

    @FXML
    private void highScores(ActionEvent event) {
        System.out.println("You clicked me!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TurnOnServer.setDisable(true);
        serverMessage.setText("  Server is Running");
        serverThread.setDaemon(true);
        serverThread.start();
        flowpaneLeft.setOrientation(Orientation.VERTICAL);
        flowpaneRight.setOrientation(Orientation.VERTICAL);
        updateOnline();

    }

    public void updateOnline() {
        playersThread =null;
        
        playersThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            flowpaneLeft.getChildren().clear();
                            flowpaneRight.getChildren().clear();

                            Server.onlinePlayersData.forEach(new BiConsumer<Integer, Player>() {
                                @Override
                                public void accept(Integer integer, Player player) {
                                    Label onlineTst = new Label(player.getUserName());
                                    onlineTst.prefWidth(150);
                                    onlineTst.setTextFill(Color.web("#D4AF37"));
                                    onlineTst.setFont(Font.font("times-new-roman", FontWeight.EXTRA_BOLD, 30));

                                    Label onlineTst1 = new Label(Integer.toString(player.getScore()));
                                    onlineTst1.prefWidth(150);
                                    onlineTst1.setTextFill(Color.web("#FFDF00"));
                                    onlineTst1.setFont(Font.font("times-new-roman", FontWeight.EXTRA_BOLD, 30));
                                    flowpaneLeft.getChildren().add(onlineTst);
                                    flowpaneRight.getChildren().add(onlineTst1);
                                }
                            });
                            /*for (String onlineTest1 : server.onlineTest) {
                                Label onlineTst = new Label(onlineTest1);
                                onlineTst.prefWidth(150);
                                onlineTst.setTextFill(Color.web("#D4AF37"));
                                onlineTst.setFont(Font.font("times-new-roman", FontWeight.EXTRA_BOLD, 30));

                                Label onlineTst1 = new Label(onlineTest1);
                                onlineTst1.prefWidth(150);
                                onlineTst1.setTextFill(Color.web("#FFDF00"));
                                onlineTst1.setFont(Font.font("times-new-roman", FontWeight.EXTRA_BOLD, 30));
                                flowpaneLeft.getChildren().add(onlineTst);
                                flowpaneRight.getChildren().add(onlineTst1);

                            }*/
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                }
            }
        });
        playersThread.setDaemon(true);
        playersThread.start();
    }
}
