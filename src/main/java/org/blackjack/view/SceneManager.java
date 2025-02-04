package org.blackjack.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.blackjack.api.*;
import org.blackjack.exception.CantBuildClassException;

import java.util.Observable;
import java.util.Observer;

/**
 * This class is the main class of the view package.
 * Is manages the scenes, the interaction between the user and the game and the audio.
 *
 * @author Diana Pamfile
 */
public class SceneManager implements Observer {

    /**
     * Builder class.
     * It is used to create the SceneManager instance.
     */
    public static class Builder {
        private static Stage window;

        /**
         * This method build the SceneManager instance.
         *
         * @throws CantBuildClassException if the instance is already created or the window is not set.
         */
        public static void build() {
            if (instance != null) throw new CantBuildClassException("SceneManager already created!");
            if (window == null) throw new CantBuildClassException("Window not set!");

            instance = new SceneManager(window);
        }

        /**
         * This method sets the window, the primary stage of the application.
         *
         * @param window
         */
        public static void setWindow(Stage window) {
            Builder.window = window;
        }

        private Builder() {
        }
    }

    /**
     * The window of the game.
     */
    private final Stage window;

    /**
     * An instance of the SceneManager.
     */
    private static SceneManager instance;

    /**
     * The background music of the game.
     */
    private static MediaPlayer audioPlayer;

    /**
     * The sound effect of the draw card.
     */
    private MediaPlayer drawCardAudio;

    /**
     * A boolean value used to enable or disable the sound effects.
     */
    private boolean soundEffect = true;

    /**
     * Private constructor of the SceneManager.
     * This method initializes the window and starts the background music.
     *
     * @param window, the first stage of the game.
     */
    private SceneManager(Stage window) {
        this.window = window;
        window.setScene(new Scene(new AnchorPane()));
        startMusic();
    }

    /**
     * This method returns the instance of the SceneManager.
     *
     * @return the instance of the SceneManager.
     * @throws IllegalStateException if the instance of the SceneManager is not created.
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SceneManager not created! Call Builder.build() first.");
        }
        return instance;
    }


    /**
     * This method is used to display the root of the scene.
     *
     * @param root to be displayed.
     */
    public void displayRoot(Root root) {
        window.getScene().setRoot(root.getPane());
    }


    /**
     * Returns an integer representing the choice of the player.
     * 1 for Hit, 2 for Stand.
     *
     * @return the player action.
     */
    public int getPlayerAction() {
        Game gameView = (Game) Root.GAME.getWindowRoot();
        return gameView.getPlayerChoice();
    }


    /**
     * This method is used to reset the plyer choice after a game.
     * Resets the player choice to 0, a default value.
     * In this way the player can choose again 1 for Hit or 2 for Stand.
     */
    public void resetPlayerChoice() {
        Game gameView = (Game) Root.GAME.getWindowRoot();
        gameView.resetPlayerChoice();
    }

    /**
     * Returns the bet of the player from the menu.
     *
     * @return the bet the player choose.
     */
    public int getBet() {
        Menu menuView = (Menu) Root.MENU.getWindowRoot();
        return menuView.getPlayerBet();

    }


    /**
     * With this method the hidden card of the dealer is revealed and the score is updated in the game view.
     *
     * @param score of the dealer.
     */
    public void removeHiddenCard(int score) {
        Game gameView = (Game) Root.GAME.getWindowRoot();
        Platform.runLater(() -> gameView.revealHiddenCard(score));
    }


    /**
     * Display the message "play again" at the end of the game.
     * The player can choose to play again or not.
     */
    public void showPlayAgain() {
        Game gameView = (Game) Root.GAME.getWindowRoot();
        Platform.runLater(() -> gameView.askToPlayAgain());

    }

    /**
     * A getter used to get the player choice to play another game.
     *
     * @return true if the player wants to play again, false otherwise.
     */
    public boolean getPlayAgain() {
        Game gameView = (Game) Root.GAME.getWindowRoot();
        return gameView.getPlayAgain();
    }


    /**
     * This method is used to reset the player choice of the number of players in a game.
     */
    public void resetPlayerSel() {
        Menu menuView = (Menu) Root.MENU.getWindowRoot();
        menuView.resetPlayerSelected();
    }

    /**
     * It starts the backgorund music of the game.
     * The music is played in loop.
     */
    public void startMusic() {
        if (audioPlayer == null) {
            String musicPath = getClass().getResource("/org/blackjack/view/audio/sound.mp3").toString();
            Media media = new Media(musicPath);
            audioPlayer = new MediaPlayer(media);
            audioPlayer.setOnEndOfMedia(() -> audioPlayer.seek(audioPlayer.getStartTime()));
            audioPlayer.setVolume(0.4);
            audioPlayer.play();
        }
    }

    /**
     * It adjusts the volume of the background music.
     *
     * @param volume, a double value which is taken from the slider in the settings.
     */
    public void setVolume(double volume) {
        if (audioPlayer != null) {
            audioPlayer.setVolume(volume);
        }
    }

    /**
     * It plays the sound effect of a draw card.
     */
    public void drawCardAudio() {
        if (soundEffect) {
            if (drawCardAudio == null) {
                String musicPath = getClass().getResource("/org/blackjack/view/audio/draw_card.mp3").toString();
                Media media = new Media(musicPath);
                drawCardAudio = new MediaPlayer(media);
                drawCardAudio.setVolume(0.4);
            } else {
                drawCardAudio.stop();
                drawCardAudio.seek(Duration.ZERO);
            }
            drawCardAudio.play();
        }
    }

    /**
     * This method enables or disables the sound effects.
     *
     * @param soundEffect, a boolean value which is taken from the settings.
     */
    public void setEffects(boolean soundEffect) {
        this.soundEffect = soundEffect;
        if (!soundEffect) {
            if (drawCardAudio != null) {
                drawCardAudio.stop();
            }
        }
    }


    /**
     * This method is the override of the update method of the Observer interface.
     * It is used to handle the data packages sends from the model,
     * and update the view.
     *
     * @param o   the observable object.
     * @param arg the data package received.
     */
    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof DataPackage dataPackage) {
            Game gameView = (Game) Root.GAME.getWindowRoot();
            Profile profileView = (Profile) Root.PROFILE.getWindowRoot();
            switch (dataPackage.packageType()) {

                case SETUP -> {
                    SetupPackage setupPackage = (SetupPackage) dataPackage;
                    Platform.runLater(() -> gameView.displayPlayers(setupPackage.playerNames(), setupPackage.numberOfPlayers(), setupPackage.avatars()));

                }
                case DRAW -> {
                    DrawPackage drawPackage = (DrawPackage) dataPackage;
                    // System.out.println("Draw package received " + drawPackage.player());
                    Platform.runLater(() -> gameView.drawCards(drawPackage.value(), drawPackage.suit(), drawPackage.player(), drawPackage.score()));
                }
                case HIT -> {
                    HitPackage hitPackage = (HitPackage) dataPackage;
                    Platform.runLater(() -> gameView.drawHitCard(hitPackage.value(), hitPackage.suit(), hitPackage.score(), hitPackage.typePlayer()));
                }
                case BUST -> {
                    BustPackage bustPackage = (BustPackage) dataPackage;
                    Platform.runLater(() -> gameView.showMessage(bustPackage.typePlayer(), MessageType.BUST));
                }
                case WIN -> {
                    WinPackage winPackage = (WinPackage) dataPackage;
                    Platform.runLater(() -> gameView.showMessage(winPackage.typePlayer(), MessageType.WIN));
                }
                case TIE -> {
                    TiePackage tiePackage = (TiePackage) dataPackage;
                    Platform.runLater(() -> gameView.showMessage(tiePackage.typePlayer(), MessageType.TIE));
                }
                case LOSE -> {
                    LosePackage losePackage = (LosePackage) dataPackage;
                    Platform.runLater(() -> gameView.showMessage(losePackage.typePlayer(), MessageType.LOSE));
                }
                case BLACKJACK -> {
                    BlackJackPackage blackJackPackage = (BlackJackPackage) dataPackage;
                    Platform.runLater(() -> gameView.showMessage(blackJackPackage.typePlayer(), MessageType.BLACKJACK));
                }
                case DRAW_DEALER -> {
                    DrawDealerCardPackage drawDealerPackage = (DrawDealerCardPackage) dataPackage;
                    // System.out.print("Draw dealer card");
                    Platform.runLater(() -> gameView.drawDealerCard(drawDealerPackage.value(), drawDealerPackage.suit(), drawDealerPackage.score(), drawDealerPackage.visible()));
                }
                case RESET -> {
                    //     System.out.println("PAcchetto arrivato");
                    ResetPackage resetPackage = (ResetPackage) dataPackage;
                    Platform.runLater(() -> {
                        gameView.resetGame(resetPackage.typePlayer());
                    });

                }
                case UPDATE -> {
                    UpdatePackage updatePackage = (UpdatePackage) dataPackage;
                    Platform.runLater(() -> {
                        System.out.println("Update package received");
                        profileView.updateProfile(updatePackage.username(), updatePackage.avatar());
                        profileView.updateLevel(updatePackage.level());
                        profileView.updateStats(updatePackage.totalGames(), updatePackage.wonGames(), updatePackage.lostGames(), updatePackage.totalFiches());
                    });

                }
            }
        }
    }


}
