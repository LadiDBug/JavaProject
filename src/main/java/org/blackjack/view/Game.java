package org.blackjack.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.blackjack.model.Dealer;
import org.blackjack.model.Player;

import java.util.List;


public class Game implements WindowRoot {
    private final AnchorPane gamePane;
    private ImageView currentPlayerAvatar;


    public Game() {
        gamePane = new AnchorPane();

        //Avatar del player
        currentPlayerAvatar = new ImageView(takePlayerImagePath());
        currentPlayerAvatar.setFitHeight(30);
        currentPlayerAvatar.setFitWidth(30);

        // Box delle infoVarie
        Label labelTurn = new Label("Sta giocando: ");
        Label labelPlayerName = new Label("PlayerName");
        HBox infoBox = new HBox(labelTurn, labelPlayerName, currentPlayerAvatar);
        infoBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");

        //Box per la gestione delle fiches
        Label labelFiches = new Label("Fiches Totali: ");
        Label totalFiches = new Label(getFiches());
        VBox boxTotalFiches = new VBox(labelFiches, totalFiches);


        Label labelBet = new Label("Puntata");
        Button f20 = new Button("20");
        Button f50 = new Button("50");
        Button f100 = new Button("100");
        Button f200 = new Button("200");
        HBox boxFiches = new HBox(f20, f50, f100, f200);

        HBox managerFichesBox = new HBox(boxTotalFiches, boxFiches);
        managerFichesBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");

        AnchorPane.setTopAnchor(infoBox, 10.0);
        AnchorPane.setLeftAnchor(infoBox, 10.0);
        AnchorPane.setBottomAnchor(managerFichesBox, 10.0);
        AnchorPane.setLeftAnchor(managerFichesBox, 10.0);

        gamePane.getChildren().addAll(infoBox, managerFichesBox);

        gamePane.setStyle(
                "-fx-background-image: url('/org/blackjack/view/blackjack_table.png');" +
                        "-fx-background-size: 100% 100%;" +
                        "-fx-background-position: center center;" +
                        "-fx-background-repeat: no-repeat;"
        );

    }

    public void displayPlayers(List<Player> players, Dealer dealer) {
        removePlayerBoxes();

        //aggiungo dealer
        VBox dealerBox = createPlayerBox(dealer.getUsername(), Integer.toString(dealer.getScore()), dealer.getAvatar());
        positionPlayerBox(dealerBox, "dealer");
        gamePane.getChildren().add(dealerBox);

        //Aggiungo ciascun giocatore
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            VBox playerBox = createPlayerBox(player.getUsername(), Integer.toString(player.getScore()), player.getAvatar());


            // if per la posizione ;dei player
            if (i == 0) {
                positionPlayerBox(playerBox, "human");
            } else if (i == 1) {
                positionPlayerBox(playerBox, "bot1");
            } else if (i == 2) {
                positionPlayerBox(playerBox, "bot2");
            } else if (i == 3) {
                positionPlayerBox(playerBox, "bot3");
            }

            gamePane.getChildren().add(playerBox);
        }


    }

    private void removePlayerBoxes() {
        gamePane.getChildren().removeIf(node -> node instanceof VBox);
    }

    private VBox createPlayerBox(String playerName, String totalPoints, String imagePath) {
        Label totalPointsLabel = new Label(totalPoints);
        Label playerLabel = new Label(playerName);
        ImageView playerAvatar = new ImageView(imagePath);
        playerAvatar.setFitHeight(30);
        playerAvatar.setFitWidth(30);

        ImageView card1 = new ImageView(getCardImagePath());
        card1.setFitHeight(80);
        card1.setFitWidth(50);
        ImageView card2 = new ImageView(getCardImagePath());
        card2.setFitHeight(80);
        card2.setFitWidth(50);

        HBox playerCards = new HBox(card1, card2);
        playerCards.setSpacing(10);

        VBox playerBox = new VBox(totalPointsLabel, playerAvatar, playerLabel, playerCards);
        playerBox.setPrefSize(100, 150);
        playerBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10;");

        return playerBox;
    }

    private void positionPlayerBox(VBox playerBox, String role) {
        switch (role) {
            case "dealer":
                AnchorPane.setTopAnchor(playerBox, 20.0);
                AnchorPane.setLeftAnchor(playerBox, 590.0);
                break;

            case "human":
                AnchorPane.setBottomAnchor(playerBox, 100.0);
                AnchorPane.setRightAnchor(playerBox, 400.0);
                break;

            case "bot1":
                AnchorPane.setBottomAnchor(playerBox, 100.0);
                AnchorPane.setLeftAnchor(playerBox, 400.0);
                break;

            case "bot2":
                AnchorPane.setBottomAnchor(playerBox, 250.0);
                AnchorPane.setLeftAnchor(playerBox, 100.0);
                break;

            case "bot3":
                AnchorPane.setBottomAnchor(playerBox, 250.0);
                AnchorPane.setRightAnchor(playerBox, 100.0);
                break;

            default:
                throw new IllegalArgumentException("Role not valid");
        }
    }


    @Override
    public Parent getPane() {
        return gamePane;
    }

    public String getTotalPoints() {
        return "10";
    }

    public String getFiches() {
        return "2000";
    }

    public String takePlayerImagePath() {
        return "/org/blackjack/view/defaultUser.png";
    }

    public String getCardImagePath() {
        return "/org/blackjack/view/retro_card.png";
    }

}
