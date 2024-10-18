package org.blackjack.blackjack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.blackjack.view.SceneManager;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage window) throws IOException {

        SceneManager sceneManager = new SceneManager(window);


        // disply del menu
        // setting base della finestra
        //aggiungere observable
        window.show();



        //window.show per far partire la finestra
    }

    public static void main(String[] args) {
        launch();
    }
}