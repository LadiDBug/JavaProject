package org.blackjack.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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


        //TODO: box verticale per un singolo player (che è la stessa anche per il dealer)
        //per la box del player mi serve l'immagine del player, il nome del player, le fiches del player
        //le carte del player, il totale delle carte del player
        Label totalPoints = new Label("20");  //mettere funziona che lo getta dal model
        Label playerName = new Label("PlayerName");
        ImageView playerAvatar = new ImageView(takePlayerImagePath());
        playerAvatar.setFitHeight(30);
        playerAvatar.setFitWidth(30);


        VBox playerBox = new VBox(totalPoints, playerAvatar, playerName);

        playerBox.setPrefSize(100, 100);
        playerBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");


        //TODO: box orizzontale per tutti i player


        AnchorPane.setTopAnchor(infoBox, 10.0);
        AnchorPane.setLeftAnchor(infoBox, 10.0);
        AnchorPane.setBottomAnchor(managerFichesBox, 10.0);
        AnchorPane.setLeftAnchor(managerFichesBox, 10.0);
        AnchorPane.setTopAnchor(playerBox, 50.0);
        AnchorPane.setLeftAnchor(playerBox, 550.0);  //posizione del dealer
        gamePane.getChildren().addAll(infoBox, managerFichesBox, playerBox);
        gamePane.setStyle(
                "-fx-background-image: url('/org/blackjack/view/blackjack_table.png');" +
                        "-fx-background-size: 100% 100%;" +
                        "-fx-background-position: center center;" +
                        "-fx-background-repeat: no-repeat;"
        );

        //HBox players = new HBox();
        //VBox singlePlayer = new VBox();
    }

    //TODO: sistemare i placeholder delle immagini
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


}
