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

    /**
     * Constructor for the Game class.
     * It initializes the gamePAne and sets up the UI elements.
     */
    public Game() {
        gamePane = new AnchorPane();

        //Create the info box, the box that shows up how is playing
        HBox infoBox = creaInfoBox();
        infoBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10; -fx-text-fill: white;");


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
        infoBox.setLayoutX(1080);
        infoBox.setLayoutY(10);
        choiceBox.setLayoutX(1000);
        choiceBox.setLayoutY(600);
        deck.setLayoutX(597);
        deck.setLayoutY(300);
        deck.setFitHeight(120);
        deck.setFitWidth(87);

        // Add elements to the game pane
        gamePane.getChildren().addAll(infoBox, choiceBox, deck, backButton);

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
     * It also creates a dialog box to confirm the exit from the game.
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

    private void showConfirmDialog() {
        Stage dialog = new Stage();
        // Utility = minimal
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.APPLICATION_MODAL); // Blocca interazione con la finestra principale

        Label confirmLabel = new Label("Are you sure?");
        Button yesButton = new Button("Yes");
        yesButton.setStyle(
                "-fx-background-color: #007f00; -fx-text-fill: white; -fx-font-size: 14px;"
                // "-fx-border-color: gold; -fx-border-width: 2px; -fx-border-radius: 5px;"
        );
        yesButton.setOnAction(event -> {
            dialog.close();
            SceneManager.getInstance().displayRoot(Root.MENU);
        });

        Button noButton = new Button("No");
        noButton.setStyle(
                "-fx-background-color: #b30000; -fx-text-fill: white; -fx-font-size: 14px;"
                //      "-fx-border-color: gold; -fx-border-width: 2px; -fx-border-radius: 5px;"
        );
        noButton.setOnAction(event -> dialog.close());

        HBox buttons = new HBox(10, yesButton, noButton);
        buttons.setAlignment(Pos.CENTER);

        VBox dialogLayout = new VBox(10, confirmLabel, buttons);
        dialogLayout.setAlignment(Pos.CENTER);
        dialogLayout.setStyle(
                "-fx-background-color: #32CD32; -fx-border-color: gold; -fx-border-width: 3px; " +
                        "-fx-border-radius: 10; -fx-background-radius: 10;");


        Scene dialogScene = new Scene(dialogLayout, 200, 100);
        dialogScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }


    /**
     * This method creates the choice box.
     * The choice box contains the buttons "Hit" and "Stand".
     *
     * @return the choice box (HBox)
     */
    public HBox creaChoiceBox() {
        Button hit = new Button();
        Button stand = new Button();

        String standButton = "-fx-background-color: rgb(200, 32, 21);"
                + "-fx-text-fill: black;"                        // Testo bianco (se serve)
                + "-fx-font-size: 14px;"                         // Dimensione del testo
                + "-fx-font-weight: bold;"                       // Testo in grassetto
                + "-fx-border-radius: 50%;"                      // Bordo arrotondato
                + "-fx-background-radius: 50%;"                 // Sfondo arrotondato
                + "-fx-padding: 10px;"                           // Padding interno
                + "-fx-min-width: 60px;"                         // Larghezza minima
                + "-fx-min-height: 60px;"                        // Altezza minima
                + "-fx-cursor: hand;"                            // Cambio cursore
                + "-fx-alignment: center;";                      // Allineamento centrale


        String hitButton = "-fx-background-color: rgb(0, 102, 0);"
                + "-fx-text-fill: black;"                        // Testo bianco (se serve)
                + "-fx-font-size: 14px;"                         // Dimensione del testo
                + "-fx-font-weight: bold;"                       // Testo in grassetto
                + "-fx-border-radius: 50%;"                      // Bordo arrotondato
                + "-fx-background-radius: 50%;"                 // Sfondo arrotondato
                + "-fx-padding: 10px;"                           // Padding interno
                + "-fx-min-width: 60px;"                         // Larghezza minima
                + "-fx-min-height: 60px;"                        // Altezza minima
                + "-fx-cursor: hand;"                            // Cambio cursore
                + "-fx-alignment: center;";                      // Allineamento centrale

        hit.setStyle(hitButton);
        stand.setStyle(standButton);
        DropShadow shadowHit = new DropShadow();
        shadowHit.setColor(Color.LIGHTGREEN);

        hit.setOnMouseEntered(event -> hit.setEffect(shadowHit)); // Aggiunge ombra
        hit.setOnMouseExited(event -> hit.setEffect(null));

        DropShadow shadowStand = new DropShadow();
        shadowStand.setColor(Color.ORANGERED);

        stand.setOnMouseEntered(event -> stand.setEffect(shadowStand)); // Aggiunge ombra
        stand.setOnMouseExited(event -> stand.setEffect(null));


        //aggiungo icona
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
        //playerCards.setSpacing(10);


        VBox playerBox = new VBox(10, totalPointsLabel, playerCards, avatarUsername);
        playerBox.setPrefSize(100, 150);
        //playerBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10;");
        playerBox.setAlignment(javafx.geometry.Pos.CENTER);
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

    public void showMessage(TypePlayer typePlayer, MessageType msg) {
        HBox messageBox = getMessageBox(typePlayer);
        Label message = (Label) messageBox.getChildren().get(0);
        message.setText(msg.getMessage());

        // Rimuovi messaggio dopo 3 secondi
        new Thread(() -> {
            try {
                Thread.sleep(3000); // Attendi 3 secondi
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                gamePane.getChildren().remove(messageBox); // Rimuove il messaggio
            });
        }).start();
    }

    private HBox getMessageBox(TypePlayer typePlayer) {
        HBox messageBox = new HBox();
        Label message = new Label();

        // Stile della scritta
        message.setStyle("""
                    -fx-text-fill: black;
                    -fx-font-size: 24px;
                    -fx-font-weight: bold;
                """);

        // Posiziona il messaggio sopra il player
        messageBox.setAlignment(Pos.CENTER);
        messageBox.getChildren().add(message);

        // Posiziona il messaggio dinamicamente
        positionMessageBox(messageBox, typePlayer);

        gamePane.getChildren().add(messageBox); // Aggiungi il messaggio al gamePane
        return messageBox;
    }

    private void positionMessageBox(HBox messageBox, TypePlayer typePlayer) {
        switch (typePlayer) {
            case DEALER -> {
                messageBox.setLayoutX(550); // Sopra il dealer
                messageBox.setLayoutY(0);
            }
            case PLAYER -> {
                messageBox.setLayoutX(800); // Sopra il player umano
                messageBox.setLayoutY(360);
            }
            case BOT1 -> {
                messageBox.setLayoutX(300); // Sopra il bot 1
                messageBox.setLayoutY(360);
            }
            case BOT2 -> {
                messageBox.setLayoutX(50); // Sopra il bot 2
                messageBox.setLayoutY(160);
            }
            case BOT3 -> {
                messageBox.setLayoutX(1050); // Sopra il bot 3
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
     * This method displays the hit card.
     *
     * @param value
     * @param suit
     * @param score
     * @param player
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

        ImageView card = createImageCard(value, suit);
        card.setFitHeight(120);
        card.setFitWidth(87);
        gamePane.getChildren().add(card);

        // Calcola la posizione di destinazione in base al giocatore
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

        // Crea la transizione di movimento
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), card);
        tt.setFromX(597);  // Centro di partenza
        tt.setFromY(300);  // Centro di partenza
        tt.setToX(toX);    // Posizione di arrivo
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

    }

    public void drawCards(Value value, Suit suit, TypePlayer player, int score) {
        ImageView card = createImageCard(value, suit);
        addCardToPlayerBox(card, player);
        updateScore(score, player);
    }

    public void drawDealerCard(Value value, Suit suit, int score, boolean visible) {
        ImageView card = createImageCard(value, suit);
        if (!visible) {
            card.setImage(new Image(getClass().getResource("retro_card.png").toExternalForm()));
            card.setFitHeight(120); // Imposta l'altezza della carta
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


    private ImageView createImageCard(Value value, Suit suit) {
        return new ImageView(getClass().getResource("cards/" + value.toString().toLowerCase() + "_" + suit.toString().toLowerCase() + ".png").toExternalForm());
    }

    private void addCardToPlayerBox(ImageView card, TypePlayer player) {
        StackPane playerCardStack = getStackPane(player);
        // Calcola lo spostamento per ogni carta, in modo che si sovrappongano leggermente
        int cardCount = playerCardStack.getChildren().size();
        double offsetX = cardCount * 15;

        // Sovrapponi le carte spostandole leggermente
        card.setTranslateX(offsetX);  // Sposta la carta verso destra

        // Aggiungi la carta al StackPane
        playerCardStack.getChildren().add(card);
    }

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

    public void revealHiddenCard(int score) {

        // mi prendo lo stack del dealer
        StackPane dealerCardStack = getStackPane(TypePlayer.DEALER);

        // creo la carta nascosta
        ImageView realCard = createImageCard(hiddenValue, hiddenSuit);
        realCard.setFitHeight(120);
        realCard.setFitWidth(87);

        // prendo il retro della carta
        ImageView retroCard = (ImageView) dealerCardStack.getChildren().getLast();

        //Rimuovo la cart coperta con animazione
        RotateTransition rt = new RotateTransition(Duration.millis(500), retroCard);
        rt.setAxis(Rotate.Y_AXIS);  // Ruota attorno all'asse Y
        rt.setFromAngle(0);
        rt.setFromAngle(90);

        rt.setOnFinished(event -> {
            dealerCardStack.getChildren().remove(retroCard);  // Rimuovi il retro della carta
            //dealerCardStack.getChildren().add(realCard);  // Mostra la carta finale

            RotateTransition rt1 = new RotateTransition(Duration.millis(500), realCard);
            rt1.setAxis(Rotate.Y_AXIS);
            rt1.setFromAngle(90);
            rt1.setToAngle(0);

            rt1.play();  // Avvia la seconda rotazione
            addCardToPlayerBox(realCard, TypePlayer.DEALER);
        });
        rt.play();
        updateScore(score, TypePlayer.DEALER);
    }


}
