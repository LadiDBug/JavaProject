package org.blackjack;

import javafx.application.Application;
import javafx.stage.Stage;
import org.blackjack.view.Root;
import org.blackjack.view.SceneManager;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage window) throws IOException {

        //Imposta e costruisce il SceneManager
        SceneManager.Builder.setWindow(window);
        SceneManager.Builder.build();
        SceneManager sceneManager = SceneManager.getInstance();


        //Display del menu come scena iniziale
        sceneManager.displayRoot(Root.MENU);

        // Configurazione della finestra e la mostra

        window.setWidth(1280);
        window.setHeight(720);
        window.show();


    }

    public static void main(String[] args) {
        launch();
    }
}