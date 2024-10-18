package org.blackjack.blackjack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage window) throws IOException {
        Scene scene = new Scene(new AnchorPane(), 320, 240);
        window.setTitle("BlackJack Game");
        window.setScene(scene);

        //Creare gestore delle scene
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