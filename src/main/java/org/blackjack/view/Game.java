package org.blackjack.view;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.blackjack.model.Suit;
import org.blackjack.model.Value;

import java.util.List;

/**
 * This class is the view of the game.
 * It sets up the game interface and manages the display of the game elements.
 */

public class Game implements WindowRoot {

    private final AnchorPane gamePane;
    private ImageView currentPlayerAvatar;
    private VBox playerBox;
    private VBox dealerBox;
    private VBox bot1Box;
    private VBox bot2Box;
    private VBox bot3Box;
    private int playerChoice;

    /**
     * Constructor for the Game class.
     * It initializes the gamePAne and sets up the UI elements.
     */
    public Game() {
        gamePane = new AnchorPane();

        //Create the info box, the box that shows up how is playing
        HBox infoBox = creaInfoBox();
        infoBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");

        //Create the fiches manager box
        HBox managerFichesBox = creaManagerFichesBox();
        managerFichesBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");

        //Create the choice box
        HBox choiceBox = creaChoiceBox();
        choiceBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");

        // Set up the card deck image
        ImageView deck = new ImageView(getClass().getResource("retro_card.png").toExternalForm());

        // Position UI elements in the gamePane
        infoBox.setLayoutX(10);
        infoBox.setLayoutY(10);
        managerFichesBox.setLayoutX(10);
        managerFichesBox.setLayoutY(640);
        choiceBox.setLayoutX(1050);
        choiceBox.setLayoutY(650);
        deck.setLayoutX(597);
        deck.setLayoutY(300);
        deck.setFitHeight(120);
        deck.setFitWidth(87);

        // Add elements to the game pane
        gamePane.getChildren().addAll(infoBox, managerFichesBox, choiceBox, deck);

        //Style of the gamePane
        gamePane.setStyle(
                "-fx-background-image: url('/org/blackjack/view/blackjack_table.png');" +
                        "-fx-background-size: 100% 100%;" +
                        "-fx-background-position: center center;" +
                        "-fx-background-repeat: no-repeat;"
        );

    }


    /**
     * This method returns the gamePane.
     *
     * @return the gamePane
     */
    @Override
    public Parent getPane() {
        return gamePane;
    }

    /**
     * This method creates the choice box.
     * The choice box contains the buttons "Hit" and "Stand".
     *
     * @return the choice box (HBox)
     */
    public HBox creaChoiceBox() {
        Button hit = new Button("Hit");
        Button stand = new Button("Stand");

        hit.setOnAction(event -> setPlayerAction(1));
        stand.setOnAction(event -> setPlayerAction(2));
        return new HBox(hit, stand);
    }

    /**
     * Resets the player choice to 0, a default value.
     */
    public void resetPlayerChoice() {
        this.playerChoice = 0;
    }

    /**
     * Sets the player action.
     *
     * @param choice, the action chosen by the player. 1 for "Hit", 2 for "Stand".
     */
    public void setPlayerAction(int choice) {
        this.playerChoice = choice;
    }

    /**
     * Gets the player's current action choice.
     *
     * @return the player's choice
     */
    public int getPlayerChoice() {
        return playerChoice;
    }

    /**
     * This method creates the info box.
     * The info box contains the player's avatar and name.
     *
     * @return the info box (HBox)
     */
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


    //TODO: Implementare bene la FICHES per farla apparire per prima

    /**
     * This method creates the fiches manager box.
     * The fiches manager box contains the total number of fiches and the buttons to bet.
     *
     * @return the fiches manager box (HBox)
     */
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


    /**
     * This methos diplayes the player boxes for each player based on the number of players.
     *
     * @param playerNames
     * @param numberOfPlayers
     * @param avatars
     */
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

    /**
     * This method removes the player boxes from the gamePane.
     */
    private void removePlayerBoxes() {
        gamePane.getChildren().removeIf(VBox.class::isInstance);
    }

    /**
     * Creates the playerBox for each player.
     *
     * @param playerName
     * @param imagePath
     * @return the playerBox (VBox)
     */
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


    /**
     * This method positions the playerBox based on the player's role.
     *
     * @param playerBox
     * @param role
     */
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

    // MESSAGE BOXES
    public void showLoseMessage(TypePlayer typePlayer) {
        ((Label) (messageBox(typePlayer).getChildren().get(0))).setText("Hai perso!");
        //messageBox().setLayoutX(500);
        //messageBox().setLayoutY(360);
    }

    public void showBJMessage(TypePlayer typePlayer) {
        ((Label) (messageBox(typePlayer).getChildren().get(0))).setText("Hai fatto BLACKJACK!");
        //messageBox().setLayoutX(500);
        //messageBox().setLayoutY(360);
    }

    public void showBustMessage(TypePlayer typePlayer) {
        ((Label) (messageBox(typePlayer).getChildren().get(0))).setText("Hai sballato!");
        //messageBox().setLayoutX(500);
        //messageBox().setLayoutY(360);
    }

    public void showWinMessage(TypePlayer typePlayer) {
        ((Label) (messageBox(typePlayer).getChildren().get(0))).setText("Hai vinto!");
        //messageBox().setLayoutX(500);
        //messageBox().setLayoutY(360);
    }

    public void showTieMessage(TypePlayer typePlayer) {
        ((Label) (messageBox(typePlayer).getChildren().get(0))).setText("E' finita in parità!");
        //messageBox().setLayoutX(500);
        //messageBox().setLayoutY(360);
    }

    /**
     * This method creates a message box for each player.
     *
     * @param typePlayer
     * @return
     */
    public HBox messageBox(TypePlayer typePlayer) {
        HBox messageBox = new HBox();
        Label message = new Label();
        message.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");
        messageBox.getChildren().add(message);
        switch (typePlayer) {
            case PLAYER -> messageBox.setLayoutX(800);
            case BOT1 -> messageBox.setLayoutX(300);
            case BOT2 -> messageBox.setLayoutX(50);
            case BOT3 -> messageBox.setLayoutX(1050);
        }

        gamePane.getChildren().add(messageBox);
        return messageBox;
    }

    /**
     * This method displays the hit card with an animation.
     *
     * @param value
     * @param suit
     * @param score
     * @param player
     */
    public void drawHitCard(Value value, Suit suit, int score, TypePlayer player) {

        drawAnimation(value, suit, player, score);

    }

    /**
     * This method displays the hit card.
     *
     * @param value
     * @param suit
     * @param score
     * @param player
     */
    private void drawHitCard1(Value value, Suit suit, int score, TypePlayer player) {
        switch (player) {
            case PLAYER -> {
                ImageView card = new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
                ((HBox) playerBox.getChildren().get(3)).getChildren().remove(0);
                ((HBox) playerBox.getChildren().get(3)).getChildren().add(card);
                ((Label) playerBox.getChildren().get(0)).setText("Punteggio: " + score);
            }
            case BOT1 -> {
                ImageView card = new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
                ((HBox) bot1Box.getChildren().get(3)).getChildren().remove(0);
                ((HBox) bot1Box.getChildren().get(3)).getChildren().add(card);
                ((Label) bot1Box.getChildren().get(0)).setText("Punteggio: " + score);

            }
            case BOT2 -> {
                ImageView card = new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
                ((HBox) bot2Box.getChildren().get(3)).getChildren().remove(0);
                ((HBox) bot2Box.getChildren().get(3)).getChildren().add(card);
                ((Label) bot2Box.getChildren().get(0)).setText("Punteggio: " + score);
            }
            case BOT3 -> {
                ImageView card = new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
                ((HBox) bot3Box.getChildren().get(3)).getChildren().remove(0);
                ((HBox) bot3Box.getChildren().get(3)).getChildren().add(card);
                ((Label) bot3Box.getChildren().get(0)).setText("Punteggio: " + score);
            }
            case DEALER -> {
                ImageView card = new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
                ((HBox) dealerBox.getChildren().get(3)).getChildren().remove(0);
                ((HBox) dealerBox.getChildren().get(3)).getChildren().add(card);
                ((Label) dealerBox.getChildren().get(0)).setText("Punteggio: " + score);
            }
        }
    }

    //TODO: sistemare le animazioni. Perchè vanno sempre al player

    /**
     * This method manages the animation of the card drawn.
     *
     * @param value
     * @param suit
     * @param player
     * @param score
     */
    private void drawAnimation(Value value, Suit suit, TypePlayer player, int score) {
        ImageView card = new ImageView(getClass().getResource("retro_card.png").toExternalForm());
        card.setFitHeight(120);
        card.setFitWidth(87);
        gamePane.getChildren().add(card);
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), card);
        tt.setFromX(597);
        tt.setFromY(300);
        tt.setToX(800);
        tt.setToY(400);

        tt.setOnFinished(event -> {
            gamePane.getChildren().remove(card);
            drawHitCard1(value, suit, score, player);
        });

        RotateTransition rt = new RotateTransition(Duration.millis(500), card);
        rt.setByAngle(90);
        //rt.setAxis(Y_AXIS);//TODO: cercare per mettere asse giusto
        RotateTransition rt1 = new RotateTransition(Duration.millis(500), card);
        rt1.setByAngle(90);
        //TODO: mettere assse Y

        rt.setOnFinished(event -> {
            card.setImage(new Image((getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm())));
            rt1.play();
        });
        rt.play();
        tt.play();

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


}
