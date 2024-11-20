package org.blackjack.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.blackjack.model.Suit;
import org.blackjack.model.Value;

import java.util.List;


public class Game implements WindowRoot {
    private final AnchorPane gamePane;
    private ImageView currentPlayerAvatar;
    private VBox playerBox;
    private VBox dealerBox;
    private VBox bot1Box;
    private VBox bot2Box;
    private VBox bot3Box;
    private int playerChoice;

    public Game() {
        gamePane = new AnchorPane();

        //Creazione dellqa infoBx
        HBox infoBox = creaInfoBox();
        infoBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");

        //Creazione della fiches box
        HBox managerFichesBox = creaManagerFichesBox();
        managerFichesBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");

        //creazione della choice box
        HBox choiceBox = creaChoiceBox();
        choiceBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");


        //Posizionamento delle box nella view
        infoBox.setLayoutX(10);
        infoBox.setLayoutY(10);
        managerFichesBox.setLayoutX(10);
        managerFichesBox.setLayoutY(640);
        choiceBox.setLayoutX(1050);
        choiceBox.setLayoutY(650);


        gamePane.getChildren().addAll(infoBox, managerFichesBox, choiceBox);

        //Stile del gamePane
        gamePane.setStyle(
                "-fx-background-image: url('/org/blackjack/view/blackjack_table.png');" +
                        "-fx-background-size: 100% 100%;" +
                        "-fx-background-position: center center;" +
                        "-fx-background-repeat: no-repeat;"
        );
    }


    @Override
    public Parent getPane() {
        return gamePane;
    }

    public HBox creaChoiceBox() {
        Button hit = new Button("Hit");
        Button stand = new Button("Stand");
        Button doubleDown = new Button("Double Down");
        Button split = new Button("Split");

        hit.setOnAction(event -> setPlayerAction(1));

        stand.setOnAction(event -> setPlayerAction(2));
        doubleDown.setOnAction(event -> setPlayerAction(3));
        split.setOnAction(event -> setPlayerAction(4));
        return new HBox(hit, stand, doubleDown, split);
    }

    public void resetPlayerChoice() {
        this.playerChoice = 0;
    }

    public void setPlayerAction(int choice) {
        this.playerChoice = choice;
    }

    public int getPlayerChoice() {
        return playerChoice;
    }

    public HBox creaInfoBox() {
        //TODO: Implementare correttamente la infoBox
        //Avatar del player
        currentPlayerAvatar = new ImageView("/org/blackjack/view/defaultUser.png");
        currentPlayerAvatar.setFitHeight(30);
        currentPlayerAvatar.setFitWidth(30);

        // Box delle infoVarie
        Label labelTurn = new Label("Sta giocando: ");
        Label labelPlayerName = new Label("PlayerName");
        return new HBox(labelTurn, labelPlayerName, currentPlayerAvatar);
    }

    public HBox creaManagerFichesBox() {

        //TODO: Sistemare la gestione delle fiches
        Label labelFiches = new Label("Fiches Totali: ");
        Label totalFiches = new Label("1000");
        VBox boxTotalFiches = new VBox(labelFiches, totalFiches);


        Label labelBet = new Label("Puntata");
        Button f20 = new Button("20");
        Button f50 = new Button("50");
        Button f100 = new Button("100");
        Button f200 = new Button("200");
        HBox boxFiches = new HBox(f20, f50, f100, f200);

        return new HBox(boxTotalFiches, boxFiches);

    }

    public void displayPlayers(List<String> playerNames, int numberOfPlayers, List<String> avatars) {
        //rimuovo le playerbox precedenti per poter vedere di volta in volta le playerbox in base a quant giocatori scelti ci sono
        removePlayerBoxes();
        //aggiungo il dealer come prima box
        dealerBox = createPlayerBox("DEALER", getClass().getResource("croupier.png").toExternalForm());
        positionPlayerBox(dealerBox, "dealer");
        gamePane.getChildren().add(dealerBox);

        //Aggiungo ciascun giocatore
        for (int i = 0; i < numberOfPlayers; i++) {

            // if per la posizione ;dei player SI PUO USARE UNO SWITCH
            switch (i) {
                case 0 -> {
                    playerBox = createPlayerBox(playerNames.get(i), avatars.get(i));
                    positionPlayerBox(playerBox, "human");
                    gamePane.getChildren().add(playerBox);
                }
                case 1 -> {
                    bot1Box = createPlayerBox(playerNames.get(i), avatars.get(i));
                    positionPlayerBox(bot1Box, "bot1");
                    gamePane.getChildren().add(bot1Box);
                }
                case 2 -> {
                    bot2Box = createPlayerBox(playerNames.get(i), avatars.get(i));
                    positionPlayerBox(bot2Box, "bot2");
                    gamePane.getChildren().add(bot2Box);
                }
                case 3 -> {
                    bot3Box = createPlayerBox(playerNames.get(i), avatars.get(i));
                    positionPlayerBox(bot3Box, "bot3");
                    gamePane.getChildren().add(bot3Box);
                }
                default -> throw new IllegalArgumentException("Numero di giocatori non valido");
            }


        }
    }

    private void removePlayerBoxes() {
        gamePane.getChildren().removeIf(VBox.class::isInstance);
    }

    private VBox createPlayerBox(String playerName, String imagePath) {
        Label totalPointsLabel = new Label("0");
        Label playerLabel = new Label(playerName);
        ImageView playerAvatar = new ImageView(imagePath);
        playerAvatar.setFitHeight(30);
        playerAvatar.setFitWidth(30);

        HBox playerCards = new HBox();
        playerCards.setSpacing(10);

        VBox playerBox = new VBox(totalPointsLabel, playerAvatar, playerLabel, playerCards);
        playerBox.setPrefSize(100, 150);
        playerBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10;");

        return playerBox;
    }


    private void positionPlayerBox(VBox playerBox, String role) {
        switch (role) {
            case "dealer":
                playerBox.setLayoutX(550);
                playerBox.setLayoutY(20);
                break;

            case "human":
                playerBox.setLayoutX(800);
                playerBox.setLayoutY(400);
                break;

            case "bot1":
                playerBox.setLayoutX(300);
                playerBox.setLayoutY(400);
                break;

            case "bot2":
                playerBox.setLayoutX(50);
                playerBox.setLayoutY(200);
                break;

            case "bot3":
                playerBox.setLayoutX(1050);
                playerBox.setLayoutY(200);
                break;

            default:
                throw new IllegalArgumentException("Role not valid");
        }
    }


    public void showBustMessage() {
        HBox messageBox = new HBox();
        Label bustMessage = new Label("Hai sballato!");
        bustMessage.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");
        messageBox.getChildren().add(bustMessage);
        messageBox.setLayoutX(800);
        messageBox.setLayoutY(30);
        gamePane.getChildren().add(messageBox);
    }

    public void drawUserCard(Value value, Suit suit, int score) {
        ImageView card = new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
        ((HBox) playerBox.getChildren().get(3)).getChildren().remove(0);
        ((HBox) playerBox.getChildren().get(3)).getChildren().add(card);
        ((Label) playerBox.getChildren().get(0)).setText("Punteggio: " + score);
        System.out.println(card);
    }

    public void drawCards(Value value, Suit suit, TypePlayer player, int score) {
        switch (player) {
            case PLAYER -> {
                ImageView card = new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
                ((HBox) playerBox.getChildren().get(3)).getChildren().add(card);
                ((Label) playerBox.getChildren().get(0)).setText("Punteggio: " + score);
            }
            case DEALER -> {
                ImageView card = new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
                ((HBox) dealerBox.getChildren().get(3)).getChildren().add(card);
                ((Label) dealerBox.getChildren().get(0)).setText("Punteggio: " + score);
            }
            case BOT1 -> {
                ImageView card = new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
                ((HBox) bot1Box.getChildren().get(3)).getChildren().add(card);
                ((Label) bot1Box.getChildren().get(0)).setText("Punteggio: " + score);
            }
            case BOT2 -> {
                ImageView card = new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
                ((HBox) bot2Box.getChildren().get(3)).getChildren().add(card);
                ((Label) bot2Box.getChildren().get(0)).setText("Punteggio: " + score);
            }
            case BOT3 -> {
                ImageView card = new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
                ((HBox) bot3Box.getChildren().get(3)).getChildren().add(card);
                ((Label) bot3Box.getChildren().get(0)).setText("Punteggio: " + score);
            }
        }
    }
    //TODO: Pensare alle fiches

}
