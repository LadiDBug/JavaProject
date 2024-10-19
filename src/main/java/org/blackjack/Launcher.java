package org.blackjack;

import javafx.application.Application;
import javafx.stage.Stage;
import org.blackjack.view.Root;
import org.blackjack.view.SceneManager;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage window) throws IOException {

        SceneManager.Builder.setWindow(window);
        SceneManager.Builder.build();
        SceneManager sceneManager = SceneManager.getInstance(window);


        //display del menu
        sceneManager.displayRoot(Root.MENU);

        // setting base della finestra
        window.setWidth(1280);
        window.setHeight(720);


        //aggiungere observable
        window.show();


        //window.show per far partire la finestra
    }

    public static void main(String[] args) {
        launch();
    }
}