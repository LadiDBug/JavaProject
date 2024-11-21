package org.blackjack.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.blackjack.api.DataPackage;
import org.blackjack.api.DrawPackage;
import org.blackjack.api.HitPackage;
import org.blackjack.api.SetupPackage;
import org.blackjack.exception.CantBuildClassException;

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
    public int getPlayerAction() {
        Game gameView = (Game) Root.GAME.getWindowRoot();
        return gameView.getPlayerChoice();
    }

    public void resetPlayerChoice() {
        Game gameView = (Game) Root.GAME.getWindowRoot();
        gameView.resetPlayerChoice();
    }


    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof DataPackage dataPackage) {
            Game gameView = (Game) Root.GAME.getWindowRoot();
            switch (dataPackage.packageType()) {

                case SETUP -> {
                    SetupPackage setupPackage = (SetupPackage) dataPackage;
                    Platform.runLater(() -> gameView.displayPlayers(setupPackage.playerNames(), setupPackage.numberOfPlayers(), setupPackage.avatars()));

                }
                case DRAW -> {
                    DrawPackage drawPackage = (DrawPackage) dataPackage;
                    Platform.runLater(() -> gameView.drawCards(drawPackage.value(), drawPackage.suit(), drawPackage.player(), drawPackage.score()));
                }
                case HIT -> {
                    HitPackage hitPackage = (HitPackage) dataPackage;
                    Platform.runLater(() -> gameView.drawHitCard(hitPackage.value(), hitPackage.suit(), hitPackage.score(), hitPackage.typePlayer()));
                }
                case BUST -> {
                    Platform.runLater(() -> gameView.showBustMessage());
                }
                case WIN -> {
                    Platform.runLater(() -> gameView.showWinMessage());
                }
                case TIE -> {
                    Platform.runLater(() -> gameView.showTieMessage());
                }
                case LOSE -> {
                    Platform.runLater(() -> gameView.showLoseMessage());
                }
            }
        }
    }


}
