package org.blackjack.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.blackjack.api.*;
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
    private static MediaPlayer audioPlayer;
    private MediaPlayer drawCardAudio;

    private SceneManager(Stage window) {
        this.window = window;
        window.setScene(new Scene(new AnchorPane()));
        startMusic();
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

    public int getBet() {
        Menu menuView = (Menu) Root.MENU.getWindowRoot();
        return menuView.getPlayerBet();

    }


    public void removeHiddenCard(int score) {
        Game gameView = (Game) Root.GAME.getWindowRoot();
        Platform.runLater(() -> gameView.revealHiddenCard(score));
    }


    public void showPlayAgain() {
        Game gameView = (Game) Root.GAME.getWindowRoot();
        Platform.runLater(() -> gameView.askToPlayAgain());

    }

    public boolean getPlayAgain() {
        Game gameView = (Game) Root.GAME.getWindowRoot();
        return gameView.getPlayAgain();
    }

    public void setPlayAgain(boolean play) {
        Game gameView = (Game) Root.GAME.getWindowRoot();
        gameView.setPlayAgain(play);
    }

    public void resetPlayerSel() {
        Menu menuView = (Menu) Root.MENU.getWindowRoot();
        menuView.resetPlayerSelected();
    }

    public void createProfile(String username, String imagePath) {
        Profile profileView = (Profile) Root.PROFILE.getWindowRoot();
        Platform.runLater(() -> profileView.createProfileBox(username, imagePath));
    }

    public void createStats(String totalGames, String totalWon, String totalLoses, String totalFiches) {
        Profile profileView = (Profile) Root.PROFILE.getWindowRoot();
        Platform.runLater(() -> profileView.createStats(totalGames, totalWon, totalLoses, totalFiches));
    }

    public void createLevel(String level) {
        Profile profileView = (Profile) Root.PROFILE.getWindowRoot();
        Platform.runLater(() -> profileView.createLevel(level));
    }

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

    public void playDrawCardAudio() {
        String musicPath = getClass().getResource("/org/blackjack/view/audio/card.mp3").toString();

        Media media = new Media(musicPath);
        drawCardAudio = new MediaPlayer(media);
        drawCardAudio.setVolume(0.4);
        drawCardAudio.play();
    }

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
                    // quando resetto devo rimuovere i box dei giocatori
                    // devo resettare la scelta del giocatore
                    // la bet la lascio cosi
                    //     System.out.println("PAcchetto arrivato");
                    ResetPackage resetPackage = (ResetPackage) dataPackage;
                    Platform.runLater(() -> {
                        gameView.resetGame(resetPackage.typePlayer());
                    });

                }
                case UPDATE -> {
                    UpdatePackage updatePackage = (UpdatePackage) dataPackage;
                    Platform.runLater(() -> {
                        profileView.updateProfile(updatePackage.username(), updatePackage.avatar());
                        profileView.updateLevel(updatePackage.level());
                        profileView.updateStats(updatePackage.totalGames(), updatePackage.wonGames(), updatePackage.lostGames(), updatePackage.totalFiches());
                    });

                }
            }
        }
    }


}
