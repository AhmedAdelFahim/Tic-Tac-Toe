/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import ArtificialIntelligence.Algorithms;
import TicTacToe.Board;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.ClientSideHandler;

/**
 *
 * @author islam salah
 */
public class PlayScreenView implements Initializable {


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

    Board board;
    public enum Mode {Player, AI}
    Button[][] BoardCells;
    Board.State state;
    private static Mode mode;
    private static int level = 0;
    boolean CanPlay = false;
    public void initGameBoard(){
        BoardCells  = new Button[][]{{pos_1, pos_2, pos_3},{ pos_4, pos_5, pos_6}, {pos_7, pos_8, pos_9}};

        for(int i = 0; i<3; i++){
            for(int j = 0; j  <3 ; j++){
                BoardCells[i][j].setDisable(false);
            }
        }
    }


    public void printGameBoard(){
        for (int i=0;i<board.toArray().length;i++)
            for (int j=0;j<board.toArray().length;j++)
            {
                if(board.toArray()[i][j].toString().equals("Blank"))
                    BoardCells[i][j].setText("");
                else{
                    BoardCells[i][j].setText(board.toArray()[i][j].toString());
                    BoardCells[i][j].setDisable(true);
                }
            }
    }


    public void setState(Board.State state){
        this.state = state;
    }

    public static void setMode(Mode myMode){
        mode = myMode;
    }

    public static void setLevel(int myLevel){
        level = myLevel;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        board = new Board();
        initGameBoard();
        setState(Board.State.X);
        CanPlay = true;
        play(mode, state);
    }

    @FXML
    private void Pso_1_Handeler(ActionEvent event) {
        if(CanPlay){
            pos_1.setText(board.getTurn().toString());
            pos_1.setDisable(true);
            board.move(0);
            CanPlay = false;
        }

    }

    @FXML
    private void Pso_4_Handeler(ActionEvent event) {
        if(CanPlay){
            pos_4.setText(board.getTurn().toString());
            pos_4.setDisable(true);
            board.move(3);
            CanPlay = false;
        }
    }

    @FXML
    private void Pso_2_Handeler(ActionEvent event) {
        if(CanPlay){
            pos_2.setText(board.getTurn().toString());
            pos_2.setDisable(true);
            board.move(1);
            CanPlay = false;
        }
    }

    @FXML
    private void Pso_5_Handeler(ActionEvent event) {
        if(CanPlay){
            pos_5.setText(board.getTurn().toString());
            pos_5.setDisable(true);
            System.out.println("Pos_5");
            board.move(4);
            CanPlay = false;
        }
    }

    @FXML
    private void Pso_3_Handeler(ActionEvent event) {
        if(CanPlay){
            pos_3.setText(board.getTurn().toString());
            pos_3.setDisable(true);
            board.move(2);
            CanPlay = false;
        }
    }

    @FXML
    private void Pso_7_Handeler(ActionEvent event) {
        if(CanPlay){
            pos_7.setText(board.getTurn().toString());
            pos_7.setDisable(true);
            System.out.println("Pos_7");
            board.move(6);
            CanPlay = false;
        }
    }

    @FXML
    private void Pso_8_Handeler(ActionEvent event) {
        if(CanPlay){
            pos_8.setText(board.getTurn().toString());
            pos_8.setDisable(true);
            System.out.println("Pos_8");
            board.move(7);
            CanPlay = false;
        }
    }

    @FXML
    private void Pso_6_Handeler(ActionEvent event) {
        if(CanPlay){
            pos_6.setText(board.getTurn().toString());
            pos_6.setDisable(true);
            System.out.println("Pos_6");
            board.move(5);
            CanPlay = false;
        }
    }

    @FXML
    private void Pso_9_Handeler(ActionEvent event) {
        if(CanPlay){
            pos_9.setText(board.getTurn().toString());
            pos_9.setDisable(true);
            System.out.println("Pos_9");
            board.move(8);
            CanPlay = false;
        }
    }


    public void play (Mode mode, Board.State state) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            printGameBoard();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    switch (mode){
                        case AI:
                            switch (board.getTurn()){
                                case X:
                                CanPlay=true;
                                    break;
                                case O:
                                Algorithms.miniMax(board, level);
                                    break;
                            }
                            break;
                        case Player:
                            if(board.getTurn()!=state){
//                                get from server
                            }
                            else{
                                CanPlay = true;
                            }
                            break;
                    }

                    if (board.isGameOver()) {
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                int status = board.getWinner()==state?1:0;
                                if(status>0){
                                    ClientSideHandler.getInstance().updateScore();
                                    System.out.println("score updated successfully");
                                }
                                printGameBoard();
                                String msg;
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                if(board.getWinner()==state)
                                    msg = "Congratulation! Game is Over and you won";
                                else if(board.getWinner()!= Board.State.Blank)
                                    msg = "Sorry! Game is Over and you loose";
                                else
                                    msg = "Game is Over with draw";
                                alert.setContentText("" + msg);
                                alert.show();
                            }
                        });
                        if (!tryAgain()) {
                            break;
                        }
                    }

                }
            }
        }).start();
    }


    /**
     * Reset the game if the player wants to play again.
     * @return      true if the player wants to play again
     */
    private boolean tryAgain () {
        if (promptTryAgain()) {
            board.reset();
            initGameBoard();
            CanPlay = true;
            System.out.println("Started new game.");
            System.out.println("X's turn.");
            return true;
        }

        return false;
    }

    /**
     * Ask the player if they want to play again.
     * @return      true if the player wants to play again
     */
    private boolean promptTryAgain () {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Would you like to start a new game? (Y/N): ");
            String response = sc.next();
            if (response.equalsIgnoreCase("y")) {
                return true;
            } else if (response.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Invalid input.");
        }
    }



    @FXML
    private void gameStatus(MouseEvent event) {
    }

}
