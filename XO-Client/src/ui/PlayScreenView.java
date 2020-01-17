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

    public Player currentPlayer;
    public static int otherPlayerId;

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

    @FXML
    private void Pso_1_Handeler(ActionEvent event) {
        if (CanPlay) {
            pos_1.setText(board.getTurn().toString());
            pos_1.setDisable(true);
            System.out.println("Pos_1");
            board.move(0);
            CanPlay = false;
            sendGameMove(0);
        }

    }

    @FXML
    private void Pso_4_Handeler(ActionEvent event) {
        if (CanPlay) {
            pos_4.setText(board.getTurn().toString());
            pos_4.setDisable(true);
            System.out.println("Pos_4");
            board.move(3);
            CanPlay = false;
             sendGameMove(3);
        }
    }

    @FXML
    private void Pso_2_Handeler(ActionEvent event) {
        if (CanPlay) {
            pos_2.setText(board.getTurn().toString());
            pos_2.setDisable(true);
            System.out.println("Pos_2");
            board.move(1);
            CanPlay = false;
             sendGameMove(1);
        }
    }

    @FXML
    private void Pso_5_Handeler(ActionEvent event) {
        if (CanPlay) {
            pos_5.setText(board.getTurn().toString());
            pos_5.setDisable(true);
            System.out.println("Pos_5");
            board.move(4);
            CanPlay = false;
             sendGameMove(4);
        }
    }

    @FXML
    private void Pso_3_Handeler(ActionEvent event) {
        if (CanPlay) {
            pos_3.setText(board.getTurn().toString());
            pos_3.setDisable(true);
            board.move(2);
            CanPlay = false;
             sendGameMove(2);
        }
    }

    @FXML
    private void Pso_7_Handeler(ActionEvent event) {
        if (CanPlay) {
            pos_7.setText(board.getTurn().toString());
            pos_7.setDisable(true);
            System.out.println("Pos_7");
            board.move(6);
            CanPlay = false;
             sendGameMove(6);
        }
    }

    @FXML
    private void Pso_8_Handeler(ActionEvent event) {
        if (CanPlay) {
            pos_8.setText(board.getTurn().toString());
            pos_8.setDisable(true);
            System.out.println("Pos_8");
            board.move(7);
            CanPlay = false;
             sendGameMove(7);
        }
    }

    @FXML
    private void Pso_6_Handeler(ActionEvent event) {
        if (CanPlay) {
            pos_6.setText(board.getTurn().toString());
            pos_6.setDisable(true);
            System.out.println("Pos_6");
            board.move(5);
            CanPlay = false;
             sendGameMove(5);
        }
    }

    @FXML
    private void Pso_9_Handeler(ActionEvent event) {
        if (CanPlay) {
            pos_9.setText(board.getTurn().toString());
            pos_9.setDisable(true);
            System.out.println("Pos_9");
            board.move(8);
            CanPlay = false;
             sendGameMove(8);
        }
    }

    Board board;

    public enum Mode {

        Player, AI
    }

    Button[][] BoardCells;
    Board.State state;
    private static Mode mode;
    private static double level = 0;
    boolean CanPlay = false;

    public void initGameBoard() {
        BoardCells = new Button[][]{{pos_1, pos_2, pos_3}, {pos_4, pos_5, pos_6}, {pos_7, pos_8, pos_9}};
        System.out.println(level);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                BoardCells[i][j].setDisable(false);
                BoardCells[i][j].setText("");
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

    public void setState(Board.State state) {
        this.state = state;
    }

    public static void setMode(Mode myMode) {
        mode = myMode;
    }

    public static void setLevel(double myLevel) {
        level = myLevel;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentPlayer = ClientSideHandler.getInstance().getCurrentPlayer();
        board = new Board();
        initGameBoard();
        setState(Board.State.X);
        CanPlay = true;
        play(mode, state);
    }

    public void play(Mode mode, Board.State state) {
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
//                                get from server
                            } else {
                                CanPlay = true;
                            }
                            break;
                    }

                    if (board.isGameOver()) {
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
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle(msg);
                                alert.setHeaderText(msg + " Would you like to try again ?");

                                ButtonType YesBtn = new ButtonType("Yes");
                                ButtonType NoBtn = new ButtonType("No");

                                alert.getButtonTypes().setAll(YesBtn, NoBtn);

                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == YesBtn) {
                                    board.reset();
                                    initGameBoard();
                                    CanPlay = true;
                                    play(mode, state);
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
            Scene scene = new Scene(root, 800, 500);
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
        /////use switch after handling loading game screen sfter invitstion accepted
//        switch (mode) {
//            case Player:
                HashMap<String, Object> map = new HashMap<>();
                map.put(Constant.REQUEST_TYPE, Constant.GAME_MOVE);
                map.put(Constant.SENDER_ID_KEY, currentPlayer.getId());
                // map.put(Constant.SENDER_NAME_KEY,currentPlayer.getUserName());
                map.put(Constant.RECIEVER_ID_KEY, otherPlayerId);
                map.put(Constant.MOVE_POSTION, movePos);
                PlayModeViewModel.gameMove(map);
                System.err.println(otherPlayerId);
//                break;
//            default:
//                break;
//        }

    }
}
