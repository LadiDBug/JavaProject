package org.blackjack.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.blackjack.exception.CantBuildClassException;
import org.blackjack.model.Dealer;
import org.blackjack.model.Player;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SceneManager implements Observer {


    public static class Builder {
        private static Stage window;

        public static void build() {
            if (instance != null) throw new CantBuildClassException("SceneManager already created!");
            if (window == null) throw new CantBuildClassException("Window not set!");

            instance = new SceneManager(window);
        }

        public static void setWindow(Stage window) {
            Builder.window = window;
        }

        private Builder() {
        }
    }

    private final Stage window;
    private static SceneManager instance;

    private SceneManager(Stage window) {
        this.window = window;
        window.setScene(new Scene(new AnchorPane()));
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SceneManager not created! Call Builder.build() first.");
        }
        return instance;
    }


    public void displayRoot(Root root) {
        window.getScene().setRoot(root.getPane());
    }

//mi serve un'istanza di game per potergli passare gli eventi

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Object[]) {
            Object[] data = (Object[]) arg;
            List<Player> players = (List<Player>) data[0];
            Dealer dealer = (Dealer) data[1];
            Platform.runLater(() -> inizializeGame(players, dealer));
        }
    }

    private void inizializeGame(List<Player> players, Dealer dealer) {
        Game gameView = (Game) Root.GAME.getWindowRoot();
        gameView.displayPlayers(players, dealer);
        displayRoot(Root.GAME);

    }
}
