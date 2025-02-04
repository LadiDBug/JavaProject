package org.blackjack.view;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.blackjack.model.Suit;
import org.blackjack.model.Value;

import java.util.List;

/**
 * This class is the view of the game.
 * It sets up the game interface and manages the display of the game elements.
 *
 * @author Diana Pamfile
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
    private int dealerScore = 0;
    private int hideScore;
    private ImageView hiddenCard;
    private Value hiddenValue;
    private Suit hiddenSuit;
    private boolean playAgain;


    /**
     * Constructor for the Game class.
     * It initializes the gamePane and sets up the UI elements.
     */
    public Game() {
        gamePane = new AnchorPane();


        //Create the choice box
        HBox choiceBox = creaChoiceBox();
        // choiceBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");

        // Set up the card deck image
        ImageView deck = new ImageView(getClass().getResource("retro_card.png").toExternalForm());

        // Adding the "back" button
        Button backButton = createBackButton();
        backButton.setLayoutX(10);
        backButton.setLayoutY(10);

        // Position UI elements in the gamePane

        choiceBox.setLayoutX(1000);
        choiceBox.setLayoutY(600);
        deck.setLayoutX(597);
        deck.setLayoutY(300);
        deck.setFitHeight(120);
        deck.setFitWidth(87);

        // Add elements to the game pane
        gamePane.getChildren().addAll(choiceBox, deck, backButton);

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
     * This method creates the back button.
     * The back button is used to exit the game and return to the main menu.
     * it calls the showConfirmDialog method when clicked.
     *
     * @return the back button
     */
    private Button createBackButton() {
        Button backButton = new Button();
        backButton.setStyle("-fx-background-color: trasparent");

        //Adding an icon to the button
        Image backIcon = new Image(getClass().getResource("left.png").toExternalForm());
        ImageView backIconView = new ImageView(backIcon);
        backIconView.setFitHeight(40);
        backIconView.setFitWidth(40);
        backButton.setGraphic(backIconView);

        // Adding an action on the button
        backButton.setOnAction(event -> showConfirmDialog());
        return backButton;
    }

    /**
     * This method creates a confirm dialog for the user.
     * If the user clicks "Yes", the menu is displayed.
     * If the user clicks "No", the dialog is closed.
     */
    private void showConfirmDialog() {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL); // Blocca interazione con la finestra principale

        Label confirmLabel = new Label("Are you sure?");
        Button yesButton = new Button("Yes");
        yesButton.setStyle(
                "-fx-background-color: #007f00; -fx-text-fill: white; -fx-font-size: 14px;"
        );

        yesButton.setOnAction(event -> {
            SceneManager.getInstance().resetPlayerSel();
            dialog.close();
            SceneManager.getInstance().displayRoot(Root.MENU);
        });

        Button noButton = new Button("No");
        noButton.setStyle(
                "-fx-background-color: #b30000; -fx-text-fill: white; -fx-font-size: 14px;"
        );
        noButton.setOnAction(event -> dialog.close());

        HBox buttons = new HBox(10, yesButton, noButton);
        buttons.setAlignment(Pos.CENTER);

        VBox dialogLayout = new VBox(10, confirmLabel, buttons);
        dialogLayout.setAlignment(Pos.CENTER);
        dialogLayout.setStyle(
                "-fx-background-color: #32CD32; -fx-border-color: white; -fx-border-width: 3px; " +
                        "-fx-border-radius: 10; -fx-background-radius: 10;");


        Scene dialogScene = new Scene(dialogLayout, 200, 100);
        dialogScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    /**
     * It creates a dialog box to ask the player if they want to play again.
     * If the player clicks "Yes", the game is reset.
     * If the player clicks "No", the player is returned to the main menu.
     */
    public void askToPlayAgain() {
        setPlayAgain(false);
        Stage ps = new Stage();
        ps.initStyle(StageStyle.TRANSPARENT);
        ps.setAlwaysOnTop(true);
        ps.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label("Do you want to play again?");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(
                event -> {
                    setPlayAgain(true);
                    ps.close();

                }
        );

        noButton.setOnAction(
                event -> {
                    setPlayAgain(false);
                    SceneManager.getInstance().resetPlayerSel();
                    ps.close();
                    SceneManager.getInstance().displayRoot(Root.MENU);
                }
        );

        HBox buttons = new HBox(10, yesButton, noButton);
        buttons.setAlignment(Pos.CENTER);
        VBox layout = new VBox(10, label, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #32CD32; -fx-border-color: white; -fx-border-width: 3px; -fx-border-radius: 10; -fx-background-radius: 10;");

        Scene scene = new Scene(layout, 200, 100);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        ps.setScene(scene);
        ps.showAndWait();
    }

    /**
     * Setter for the playAgain variable, used to determine if the player wants to play again.
     *
     * @param playAgain a boolean value, true if the player wants to play again, false otherwise.
     */
    public void setPlayAgain(boolean playAgain) {
        this.playAgain = playAgain;
    }

    /**
     * Getter for the playAgain variable.
     *
     * @return the value of the playAgain variable.
     */
    public boolean getPlayAgain() {
        return playAgain;
    }

    /**
     * This method creates the choice box for the player.
     * The choice box contains the buttons "Hit" and "Stand".
     *
     * @return the choice box (HBox)
     */
    public HBox creaChoiceBox() {
        Button hit = new Button();
        Button stand = new Button();

        String standButton = "-fx-background-color: rgb(200, 32, 21);"
                + "-fx-text-fill: black;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-border-radius: 50%;"
                + "-fx-background-radius: 50%;"
                + "-fx-padding: 10px;"
                + "-fx-min-width: 60px;"
                + "-fx-min-height: 60px;"
                + "-fx-cursor: hand;"
                + "-fx-alignment: center;";


        String hitButton = "-fx-background-color: rgb(0, 102, 0);"
                + "-fx-text-fill: black;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-border-radius: 50%;"
                + "-fx-background-radius: 50%;"
                + "-fx-padding: 10px;"
                + "-fx-min-width: 60px;"
                + "-fx-min-height: 60px;"
                + "-fx-cursor: hand;"
                + "-fx-alignment: center;";

        hit.setStyle(hitButton);
        stand.setStyle(standButton);
        DropShadow shadowHit = new DropShadow();
        shadowHit.setColor(Color.LIGHTGREEN);

        hit.setOnMouseEntered(event -> hit.setEffect(shadowHit));
        hit.setOnMouseExited(event -> hit.setEffect(null));

        DropShadow shadowStand = new DropShadow();
        shadowStand.setColor(Color.ORANGERED);

        stand.setOnMouseEntered(event -> stand.setEffect(shadowStand));
        stand.setOnMouseExited(event -> stand.setEffect(null));


        //aggiungo icona hit
        Image hitIcon = new Image(getClass().getResource("card_hit.png").toExternalForm());
        ImageView hitIconView = new ImageView(hitIcon);
        hitIconView.setFitHeight(40);
        hitIconView.setFitWidth(40);

        Text hitText = new Text("Hit");
        hitText.setStyle("-fx-fill: white; -fx-font-size: 14px;");

        VBox hitBox = new VBox(1, hitIconView, hitText);
        hitBox.setAlignment(javafx.geometry.Pos.CENTER);
        hit.setGraphic(hitBox);

        //icona stand
        Image standIcon = new Image(getClass().getResource("stand.png").toExternalForm());
        ImageView standIconView = new ImageView(standIcon);
        standIconView.setFitHeight(40);
        standIconView.setFitWidth(40);
        Text standText = new Text("Stand");
        standText.setStyle("-fx-fill: white; -fx-font-size: 14px;");

        VBox standBox = new VBox(1, standIconView, standText);
        standBox.setAlignment(javafx.geometry.Pos.CENTER);
        stand.setGraphic(standBox);


        hit.setPrefSize(100, 50);
        stand.setPrefSize(100, 50);

        hit.setOnAction(event -> setPlayerAction(1));
        stand.setOnAction(event -> setPlayerAction(2));

        HBox bBox = new HBox(hit, stand);
        bBox.setSpacing(20);
        return bBox;
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
     * Resets the player choice to 0, a default value, after the chioce.
     */
    public void resetPlayerChoice() {
        this.playerChoice = 0;
    }


    /**
     * Gets the player's current action choice.
     * (1 for Hit, 2 for Stand)
     *
     * @return the player's choice
     */
    public int getPlayerChoice() {
        return playerChoice;
    }


    /**
     * This method diplayes the player boxes for each player, based on the number of players.
     * It calls the positionPlayerBox method to put every box in a specific position on the pane.
     *
     * @param playerNames     to be displayed
     * @param numberOfPlayers used for the creation of player box
     * @param avatars         to be displayed
     * @throws IllegalArgumentException if the numberOfPlayers is not valid.
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
     * It is used before the display of the players.
     */
    public void removePlayerBoxes() {
        gamePane.getChildren().removeIf(VBox.class::isInstance);
    }

    /**
     * Reset the cards from the StackPane for every player in the game.
     * It also manages the dealer score seen in the game.
     *
     * @param typePlayer to know the player whose card will be removed
     */
    public void resetGame(TypePlayer typePlayer) {

        switch (typePlayer) {
            case PLAYER -> {
                StackPane playerCardStack = getStackPane(TypePlayer.PLAYER);
                playerCardStack.getChildren().clear();

            }
            case BOT1 -> {
                StackPane bot1CardStack = getStackPane(TypePlayer.BOT1);
                bot1CardStack.getChildren().clear();
            }
            case BOT2 -> {
                StackPane bot2CardStack = getStackPane(TypePlayer.BOT2);
                bot2CardStack.getChildren().clear();
            }
            case BOT3 -> {
                StackPane bot3CardStack = getStackPane(TypePlayer.BOT3);
                bot3CardStack.getChildren().clear();
            }
            case DEALER -> {
                StackPane dealerCardStack = getStackPane(TypePlayer.DEALER);
                dealerCardStack.getChildren().clear();
                dealerScore = 0;
                hideScore = 0;
            }
        }
    }


    /**
     * Creates the playerBox for each player.
     *
     * @param playerName to be displayed.
     * @param imagePath  it is the avatar of the player.
     * @return the playerBox (VBox)
     */
    private VBox createPlayerBox(String playerName, String imagePath) {
        Label totalPointsLabel = new Label("0");
        totalPointsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white");
        totalPointsLabel.setAlignment(javafx.geometry.Pos.CENTER);

        Label playerLabel = new Label(playerName);
        playerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white");
        ImageView playerAvatar = new ImageView(imagePath);
        playerAvatar.setFitHeight(30);
        playerAvatar.setFitWidth(30);

        HBox avatarUsername = new HBox(10, playerAvatar, playerLabel);
        avatarUsername.setAlignment(javafx.geometry.Pos.CENTER);

        StackPane playerCards = new StackPane();
        playerCards.setPrefSize(100, 140);

        VBox playerBox = new VBox(10, totalPointsLabel, playerCards, avatarUsername);
        playerBox.setPrefSize(100, 150);

        playerBox.setAlignment(javafx.geometry.Pos.CENTER);
        return playerBox;
    }


    /**
     * This method positions the playerBox on the gameView, based on the player's role.
     *
     * @param playerBox the box to be positioned.
     * @param role      specified the box should be placed.
     * @throws IllegalArgumentException if the role is not valid.
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

    /**
     * This method displays a message (WIN, LOSE, TIE, BLACKJACK, BUST) for a specific player.
     * The message is shown only for 3 seconds and then it is removed.
     *
     * @param typePlayer
     * @param msg        the type of message to be shown.
     */
    public void showMessage(TypePlayer typePlayer, MessageType msg) {
        HBox messageBox = getMessageBox(typePlayer);
        Label message = (Label) messageBox.getChildren().get(0);
        message.setText(msg.getMessage());

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                gamePane.getChildren().remove(messageBox);
            });
        }).start();
    }

    /**
     * It creates and display the HBox of the message, for a specific player.
     *
     * @param typePlayer
     * @return messageBox containing the message Label
     */
    private HBox getMessageBox(TypePlayer typePlayer) {
        HBox messageBox = new HBox();
        Label message = new Label();

        message.setStyle("""
                    -fx-text-fill: black;
                    -fx-font-size: 24px;
                    -fx-font-weight: bold;
                """);

        messageBox.setAlignment(Pos.CENTER);
        messageBox.getChildren().add(message);

        positionMessageBox(messageBox, typePlayer);

        gamePane.getChildren().add(messageBox);
        return messageBox;
    }

    /**
     * This method position the messageBox above a specific player.
     *
     * @param messageBox
     * @param typePlayer
     */
    private void positionMessageBox(HBox messageBox, TypePlayer typePlayer) {
        switch (typePlayer) {
            case DEALER -> {
                messageBox.setLayoutX(550);
                messageBox.setLayoutY(0);
            }
            case PLAYER -> {
                messageBox.setLayoutX(800);
                messageBox.setLayoutY(360);
            }
            case BOT1 -> {
                messageBox.setLayoutX(300);
                messageBox.setLayoutY(360);
            }
            case BOT2 -> {
                messageBox.setLayoutX(50);
                messageBox.setLayoutY(160);
            }
            case BOT3 -> {
                messageBox.setLayoutX(1050);
                messageBox.setLayoutY(160);
            }
            default -> throw new IllegalArgumentException("Invalid player type");
        }
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
     * This method displays the hit card with an overlay of the cards.
     *
     * @param value  of the card.
     * @param suit   of the card.
     * @param score  of the player.
     * @param player the player who hit a card.
     */
    private void drawHitCard1(Value value, Suit suit, int score, TypePlayer player) {

        ImageView card = createImageCard(value, suit);

        StackPane stackPlayerCard = getStackPane(player);

        int cardCount = stackPlayerCard.getChildren().size();
        double offsetX = cardCount * 15;

        card.setTranslateX(offsetX);

        stackPlayerCard.getChildren().add(card);
        updateScore(score, player);

    }


    /**
     * This method manages the animation of the card drawn.
     * It also played the sound effect of a draw card.
     *
     * @param value
     * @param suit
     * @param player
     * @param score
     */
    private void drawAnimation(Value value, Suit suit, TypePlayer player, int score) {

        ImageView card = createImageCard(value, suit);
        card.setFitHeight(120);
        card.setFitWidth(87);
        gamePane.getChildren().add(card);

        // Calcolo la posizione di destinazione in base al giocatore
        double toX = 0;
        double toY = 0;

        // Determino la posizione di dove va la carta quando viene pescata
        switch (player) {
            case PLAYER:
                toX = playerBox.getLayoutX() + playerBox.getWidth() / 2 - card.getFitWidth() / 2;
                toY = playerBox.getLayoutY() + playerBox.getHeight() / 2 - card.getFitHeight() / 2;
                break;
            case DEALER:
                toX = dealerBox.getLayoutX() + dealerBox.getWidth() / 2 - card.getFitWidth() / 2;
                toY = dealerBox.getLayoutY() + dealerBox.getHeight() / 2 - card.getFitHeight() / 2;
                break;
            case BOT1:
                toX = bot1Box.getLayoutX() + bot1Box.getWidth() / 2 - card.getFitWidth() / 2;
                toY = bot1Box.getLayoutY() + bot1Box.getHeight() / 2 - card.getFitHeight() / 2;
                break;
            case BOT2:
                toX = bot2Box.getLayoutX() + bot2Box.getWidth() / 2 - card.getFitWidth() / 2;
                toY = bot2Box.getLayoutY() + bot2Box.getHeight() / 2 - card.getFitHeight() / 2;
                break;
            case BOT3:
                toX = bot3Box.getLayoutX() + bot3Box.getWidth() / 2 - card.getFitWidth() / 2;
                toY = bot3Box.getLayoutY() + bot3Box.getHeight() / 2 - card.getFitHeight() / 2;
                break;
        }

        // Creo la transizione di movimento
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), card);
        tt.setFromX(597);
        tt.setFromY(300);
        tt.setToX(toX);
        tt.setToY(toY);

        // Aggiungi l'animazione alla scena
        tt.setOnFinished(event -> {
            gamePane.getChildren().remove(card);  // Rimuovi il retro della carta
            drawHitCard1(value, suit, score, player);  // Mostra la carta finale
        });

        // Aggiungo animazione di rotazione
        RotateTransition rt = new RotateTransition(Duration.millis(500), card);
        rt.setByAngle(90);  // Ruota di 90 gradi
        RotateTransition rt1 = new RotateTransition(Duration.millis(500), card);
        rt1.setByAngle(90);  // Ruota ancora di 90 gradi

        rt.setOnFinished(event -> {
            // Mostra la carta finale (dopo la rotazione)
            card.setImage(new Image((getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm())));
            rt1.play();  // Avvia la seconda rotazione
        });

        // Avvia le animazioni
        rt.play();
        tt.play();
        SceneManager.getInstance().drawCardAudio();
    }

    /**
     * This method add the card to the StackPane of card of the player.
     *
     * @param value
     * @param suit
     * @param player
     * @param score
     */
    public void drawCards(Value value, Suit suit, TypePlayer player, int score) {
        ImageView card = createImageCard(value, suit);
        addCardToPlayerBox(card, player);
        updateScore(score, player);
    }

    /**
     * A method for the draw card of the dealer.
     * It also manages the score depending on whether the second card has been revealed or not.
     *
     * @param value
     * @param suit
     * @param score
     * @param visible
     */
    public void drawDealerCard(Value value, Suit suit, int score, boolean visible) {
        ImageView card = createImageCard(value, suit);
        if (!visible) {
            card.setImage(new Image(getClass().getResource("retro_card.png").toExternalForm()));
            card.setFitHeight(120);
            card.setFitWidth(87);

            addCardToPlayerBox(card, TypePlayer.DEALER);

            dealerScore -= score;
            hideScore = score;
            hiddenCard = card;
            hiddenSuit = suit;
            hiddenValue = value;

        } else {
            addCardToPlayerBox(card, TypePlayer.DEALER);
            updateScore(dealerScore + score, TypePlayer.DEALER);
        }

    }


    /**
     * This method is used for the creation of the imageCard.
     *
     * @param value
     * @param suit
     * @return ImageView of the card.
     */
    private ImageView createImageCard(Value value, Suit suit) {
        return new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
    }

    /**
     * This method add the card to the playerBox with an overlay of the cards.
     *
     * @param card
     * @param player
     */
    private void addCardToPlayerBox(ImageView card, TypePlayer player) {
        StackPane playerCardStack = getStackPane(player);
        int cardCount = playerCardStack.getChildren().size();
        double offsetX = cardCount * 15;

        card.setTranslateX(offsetX);

        playerCardStack.getChildren().add(card);
    }

    /**
     * Getter for the StackPane of a player
     *
     * @param player
     * @return StackPane with the cards.
     */
    private StackPane getStackPane(TypePlayer player) {
        StackPane playerCardStack;
        switch (player) {
            case PLAYER -> playerCardStack = (StackPane) playerBox.getChildren().get(1);
            case DEALER -> playerCardStack = (StackPane) dealerBox.getChildren().get(1);
            case BOT1 -> playerCardStack = (StackPane) bot1Box.getChildren().get(1);
            case BOT2 -> playerCardStack = (StackPane) bot2Box.getChildren().get(1);
            case BOT3 -> playerCardStack = (StackPane) bot3Box.getChildren().get(1);
            default -> throw new IllegalStateException("Unexpected value: " + player);
        }
        return playerCardStack;
    }


    /**
     * Method used to update the score of a player when gets a new card.
     *
     * @param score
     * @param player
     */
    private void updateScore(int score, TypePlayer player) {
        switch (player) {
            case PLAYER -> {
                ((Label) playerBox.getChildren().get(0)).setText("Points: " + score);
            }
            case DEALER -> {
                System.out.println("Score: " + score);
                ((Label) dealerBox.getChildren().get(0)).setText("Points: " + score);
            }
            case BOT1 -> {
                ((Label) bot1Box.getChildren().get(0)).setText("Points: " + score);
            }
            case BOT2 -> {
                ((Label) bot2Box.getChildren().get(0)).setText("Points: " + score);
            }
            case BOT3 -> {
                ((Label) bot3Box.getChildren().get(0)).setText("Points: " + score);
            }
        }
    }

    /**
     * It reveals the hidden card of the dealer with an animation.
     *
     * @param score
     */
    public void revealHiddenCard(int score) {

        StackPane dealerCardStack = getStackPane(TypePlayer.DEALER);

        ImageView realCard = createImageCard(hiddenValue, hiddenSuit);
        realCard.setFitHeight(120);
        realCard.setFitWidth(87);

        ImageView retroCard = (ImageView) dealerCardStack.getChildren().getLast();

        //Rimuovo la cart coperta con animazione
        RotateTransition rt = new RotateTransition(Duration.millis(500), retroCard);
        rt.setAxis(Rotate.Y_AXIS);
        rt.setFromAngle(0);
        rt.setFromAngle(90);

        rt.setOnFinished(event -> {
            dealerCardStack.getChildren().remove(retroCard);
            realCard.setOpacity(1);
            RotateTransition rt1 = new RotateTransition(Duration.millis(500), realCard);
            rt1.setAxis(Rotate.Y_AXIS);
            rt1.setFromAngle(90);
            rt1.setToAngle(0);

            rt1.play();
            addCardToPlayerBox(realCard, TypePlayer.DEALER);
        });
        rt.play();
        updateScore(score, TypePlayer.DEALER);
    }


}

