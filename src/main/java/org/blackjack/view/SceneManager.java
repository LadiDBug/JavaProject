package org.blackjack.view;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneManager {

    private final Stage window;

    public SceneManager(Stage window){
        this.window = window;
        window.setScene(new Scene(new AnchorPane()));


    }
}
