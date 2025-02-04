package org.blackjack;

import javafx.application.Application;
import javafx.stage.Stage;
import org.blackjack.view.Root;
import org.blackjack.view.SceneManager;

import java.io.IOException;

/**
 * This class contains the main method to launch the application.
 * It extends the Application class from JavaFX.
 * There is an override of the start method that sets the window size and displays the menu scene.
 *
 * @author Diana Pamfile
 */
public class Launcher extends Application {
    @Override
    public void start(Stage window) throws IOException {


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