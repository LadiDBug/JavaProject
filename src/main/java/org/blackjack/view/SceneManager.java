package org.blackjack.view;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.blackjack.exception.CantBuildClassException;

import java.util.Observable;
import java.util.Observer;

public class SceneManager implements Observer {


    public static class Builder {
        @Setter
        private static Stage window;

        public static void build() {
            if (instance != null) throw new CantBuildClassException("SceneManager already created!");
            if (window == null) throw new CantBuildClassException("Window not set!");

            instance = new SceneManager(window);
        }

        private Builder() {
        }
    }

    private final Stage window;
    @Getter
    private static SceneManager instance;


    private SceneManager(Stage window) {
        this.window = window;
        window.setScene(new Scene(new AnchorPane()));
    }

    public void displayRoot(Root root) {
        window.getScene().setRoot(root.getPane());
    }

//mi serve un'istanza di game per potergli passare gli eventi

    @Override
    public void update(Observable o, Object arg) {
        Game game = ((Game) Root.GAME.getGame());
        //Platform.runLater((
        //TODO: switch per i packagetype
    }
}
