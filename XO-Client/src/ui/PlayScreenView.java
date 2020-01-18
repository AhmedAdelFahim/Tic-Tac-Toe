/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import ArtificialIntelligence.Algorithms;
import TicTacToe.Board;
import com.google.gson.JsonObject;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.ClientSideHandler;
import model.Player;
import utils.Constant;
import viewmodel.InvitationViewModel;
import viewmodel.PlayModeViewModel;

/**
 * @author islam salah
 */
public class PlayScreenView implements Initializable {

    public model.Player currentPlayer;

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
    private Label userNameTop;
    @FXML
    private Label userNameBottom;
    @FXML
    private Label opponentUserName;
    @FXML
    private Label userCharacter;
    @FXML
    private Label opponentCharacter;
    @FXML
    private FontAwesomeIconView list;



    @FXML
    private void Pso_1_Handeler(ActionEvent event) {

    }

    @FXML
    private void Pso_4_Handeler(ActionEvent event) {
    }

    @FXML
    private void Pso_2_Handeler(ActionEvent event) {
    }

    @FXML
    private void Pso_5_Handeler(ActionEvent event) {
    }

    @FXML
    private void Pso_3_Handeler(ActionEvent event) {
    }

    @FXML
    private void Pso_7_Handeler(ActionEvent event) {
    }

    @FXML
    private void Pso_8_Handeler(ActionEvent event) {
    }

    @FXML
    private void Pso_6_Handeler(ActionEvent event) {
    }

    @FXML
    private void Pso_9_Handeler(ActionEvent event) {
    }

    Board board;

    public enum Mode {Player, AI}
    public enum Player {Host,Guest}
    public static String PlayerName;
    public static String OpponentPlayerName;
    private static Player player = Player.Host;
    Button[][] BoardCells;
    Board.State state;
    private static Mode mode;
    private static double level = 0;
    boolean CanPlay = false;

    public void initGameBoard() {
        board.reset();
        ClientSideHandler.updateStatus(Constant.BUSY_STATUS);
        BoardCells = new Button[][]{{pos_1, pos_2, pos_3}, {pos_4, pos_5, pos_6}, {pos_7, pos_8, pos_9}};
        System.out.println(PlayerName);
        System.out.println(OpponentPlayerName);
        userNameTop.setText(PlayerName);
        userNameBottom.setText(PlayerName);
        opponentUserName.setText(OpponentPlayerName);
        setState();
        System.out.println(state.toString());
        userCharacter.setText(state.toString());
        opponentCharacter.setText((state== Board.State.X?Board.State.O:Board.State.X).toString());
        System.out.println(level);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int finalJ = j;
                int finalI = i;
                BoardCells[finalI][finalJ].setDisable(false);
                BoardCells[finalI][finalJ].setText("");
                BoardCells[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (CanPlay) {
                            BoardCells[finalI][finalJ].setText(board.getTurn().toString());
//                            convert from 2d array  to 1d
                            board.move(finalI*3+finalJ);
                            if(mode==Mode.Player)
                                sendGameMove(finalI*3+finalJ);

                            CanPlay = false;
                        }


                    }
                });
            }
        }
    }

    public void printGameBoard() {
        for (int i = 0; i < board.toArray().length; i++) {
            for (int j = 0; j < board.toArray().length; j++) {
                if (board.toArray()[i][j].toString().equals("Blank")) {
                    BoardCells[i][j].setText("");
                } else {
                    BoardCells[i][j].setText(board.toArray()[i][j].toString());
                    BoardCells[i][j].setDisable(true);
                }
            }
        }
    }

    public static void setToHost(){
        player = Player.Host;
    }

    public static void setToGuest(){
        player = Player.Guest;
    }

    public void setState() {
        if(player == Player.Host){
            state = Board.State.X;
            CanPlay = true;
        }
        else {
            state = Board.State.O;
            CanPlay = false;
        }
    }

    public static void setModeToAI() {
        mode = Mode.AI;
        PlayerName = PlayModeController.currentPlayer.getUserName();
        OpponentPlayerName = "Computer";
    }

    public static void setModeToPlayers() {
        mode = Mode.Player;
        PlayerName = PlayModeController.currentPlayer.getUserName();
        OpponentPlayerName = PlayModeController.OtherPlayer;
    }
    public static void setLevel(double myLevel) {
        level = myLevel;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentPlayer = ClientSideHandler.getInstance().getCurrentPlayer();
        board = new Board();
        initGameBoard();
        play();

    }


    public void play() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            printGameBoard();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    switch (mode) {
                        case AI:
                            switch (board.getTurn()) {
                                case X:
                                    CanPlay = true;
                                    break;
                                case O:
                                    Algorithms.miniMax(board, level);
                                    break;
                            }
                            break;
                        case Player:

                            if (board.getTurn() != state) {
                                System.out.println("hello its other player turn");
                               if(ClientSideHandler.getInstance().getOtherPlayerMove()!=-1){
                                   System.out.println("other player player");
                                   board.move(ClientSideHandler.getInstance().getOtherPlayerMove());
                                   Platform.runLater(new Runnable() {
                                       @Override
                                       public void run() {
                                           printGameBoard();
                                       }
                                   });
                                   ClientSideHandler.getInstance().setOtherPlayerMove();
                               }
                            }
                            else {
                                CanPlay = true;
                            }
                            break;
                    }

                    if (board.isGameOver()) {
                        ClientSideHandler.updateStatus(Constant.ONLINE_STATUS);
                        int status = board.getWinner() == state ? 1 : 0;
                        if (status > 0) {
                            ClientSideHandler.getInstance().updateScore();
                            System.out.println("score updated successfully");
                        }
                        String msg;
                        if (board.getWinner() == state) {
                            msg = "Congratulation! Game is Over and you won";
                        } else if (board.getWinner() != Board.State.Blank) {
                            msg = "Sorry! Game is Over and you loose";
                        } else {
                            msg = "Game is Over with draw";
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                printGameBoard();
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle(msg);
                                alert.setHeaderText(msg + " Would you like to try again ?");

                                ButtonType YesBtn = new ButtonType("Yes");
                                ButtonType NoBtn = new ButtonType("No");

                                alert.getButtonTypes().setAll(YesBtn, NoBtn);

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == YesBtn) {
                                    board.reset();
                                    System.out.println(board);
                                    initGameBoard();
                                    play();
                                } else if (result.get() == NoBtn) {
                                    goHome();
                                }

                            }
                        });
                        if (!CanPlay) {
                            break;
                        }
                    }

                }
            }
        }).start();
    }

    public void goHome() {
        ClientSideHandler.updateStatus(Constant.ONLINE_STATUS);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayMode.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) pos_1.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void gameStatus(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure ?");
        alert.setHeaderText("Are you sure you want to pause the game ?");

        ButtonType YesBtn = new ButtonType("Yes");
        ButtonType NoBtn = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(YesBtn, NoBtn);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == YesBtn) {
            Alert innerAlert = new Alert(Alert.AlertType.CONFIRMATION);
            innerAlert.setTitle("Do you want to save the game ?");
            innerAlert.setHeaderText("Do you want to save the game ?");

            ButtonType innerYesBtn = new ButtonType("Yes");
            ButtonType innerNoBtn = new ButtonType("Cancel");
            innerAlert.getButtonTypes().setAll(innerYesBtn, innerNoBtn);
            Optional<ButtonType> innerResult = innerAlert.showAndWait();

            if (innerResult.get() == innerYesBtn) {
                ClientSideHandler.saveGame(board.toString().replaceAll("\\s", ""));
            }
            goHome();
        }
    }

    public void sendGameMove(int movePos) {

                HashMap<String, Object> map = new HashMap<>();
                map.put(Constant.REQUEST_TYPE, Constant.GAME_MOVE);
                map.put(Constant.SENDER_ID_KEY, currentPlayer.getId());
                map.put(Constant.RECIEVER_ID_KEY, PlayModeController.OtherPlayerId);
                map.put(Constant.MOVE_POSTION, movePos);
                PlayModeViewModel.gameMove(map);
                System.err.println(map);

    }
}
