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
import model.ClientSideHandler;
import utils.Constant;
import viewmodel.LogoutViewModel;

import java.util.HashMap;
import javafx.scene.image.Image;

/**
 *
 * @author eg
 */
public class App extends Application {

    public static Stage CurrentStage;
    @Override
    public void start(Stage stage) throws Exception {

        CurrentStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("open.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("XOIcon.png")));
        stage.setTitle("Tic Tac Toe");

        stage.show();

    }

    @Override
    public void stop() throws Exception {
        // logout before close
        HashMap<String,Object> map = new HashMap<>();
        map.put(Constant.USER_NAME_KEY, ClientSideHandler.getInstance().getCurrentPlayer().getUserName());
        map.put(Constant.REQUEST_TYPE,Constant.LOGOUT);
        LogoutViewModel.logout(map);
        super.stop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
