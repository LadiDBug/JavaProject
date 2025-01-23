package org.blackjack.view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.blackjack.controller.GameController;

/**
 * This class represents the main menu of the game.
 * Allows access to the sections of the game, such as Game, Profile, Settings and Exit
 */
public class Menu implements WindowRoot {

    private final AnchorPane anchorPane;
    private final GameController controller;
    private int playersSelected = 1;
    private int playerBet;

    /**
     * Constructor that initializes the main menu.
     */
    public Menu() {
        this.controller = new GameController();
        anchorPane = new AnchorPane();

        // Menu Buttons
        Button b1 = new Button("Play");
        Button b2 = new Button("Profile");
        Button b3 = new Button("Settings");
        Button b4 = new Button("Exit");

        // Vbox for the buttons
        VBox vBox = new VBox(b1, b2, b3, b4);
        vBox.setStyle("-fx-background-color: transparent");
        vBox.setPrefWidth(200);
        vBox.setPrefHeight(400);
        vBox.setLayoutX(540);
        vBox.setLayoutY(100);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        // Apply button styles
        styleButton(b1);
        styleButton(b2);
        styleButton(b3);
        styleButton(b4);


        // Action for the "Play" buttom
        b1.setOnAction(e -> {
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            AnchorPane anchorPane1 = new AnchorPane();

            // Radio button per la scelta dei giocatori
            RadioButton onePlayer = new RadioButton("1");
            RadioButton twoPlayer = new RadioButton("2");
            RadioButton threePlayer = new RadioButton("3");
            RadioButton fourPlayer = new RadioButton("4");

            // Apply style for the radio buttons
            customStyle(onePlayer);
            customStyle(twoPlayer);
            customStyle(threePlayer);
            customStyle(fourPlayer);

            // ToggleGroup for the RadioButtons
            ToggleGroup numberOfPlayers = new ToggleGroup();
            onePlayer.setToggleGroup(numberOfPlayers);
            twoPlayer.setToggleGroup(numberOfPlayers);
            threePlayer.setToggleGroup(numberOfPlayers);
            fourPlayer.setToggleGroup(numberOfPlayers);

            // set default selection
            onePlayer.setSelected(true);

            numberOfPlayers.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == onePlayer) {
                    playersSelected = 1;
                } else if (newValue == twoPlayer) {
                    playersSelected = 2;
                } else if (newValue == threePlayer) {
                    playersSelected = 3;
                } else if (newValue == fourPlayer) {
                    playersSelected = 4;
                }

            });

            // Label to ask the number of players
            Label labelAskNumberPlayers = new Label("Choose the number of players: ");
            labelAskNumberPlayers.setStyle("-fx-font-size: 12px; -fx-font-family: 'Verdana'; -fx-text-fill: white;");

            // HBox for radioBotton
            HBox choicePlayers = new HBox(onePlayer, twoPlayer, threePlayer, fourPlayer);
            choicePlayers.setAlignment(Pos.CENTER);

            // Button to start the game and exit
            Button buttonStart = new Button("Start");
            Button buttonExit = new Button("Cancel");
            buttonStart.setStyle("-fx-background-colodr: #FFD700; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;"); // Oro
            buttonExit.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;"); // Rosso scuro

            // Horizontal layout for the "Start" and "Cancel" buttons
            HBox startExitBox = new HBox(20, buttonStart, buttonExit);
            startExitBox.setAlignment(Pos.CENTER);

            // Main VBox
            VBox box2 = new VBox(20, labelAskNumberPlayers, choicePlayers, startExitBox);
            box2.setAlignment(Pos.CENTER);
            box2.setStyle(
                    "-fx-background-color:#003100;" +
                            "-fx-background-position: center;" +
                            "-fx-padding: 20px; " +
                            "fx-border-radius: 20;" +
                            "-fx-background-radius: 20;"
            );

            box2.setPrefSize(300, 200);

            // Imposta stile trasparente al contenitore principale (AnchorPane)
            anchorPane1.setStyle("-fx-background-color: transparent;");
            anchorPane1.getChildren().add(box2);

            buttonExit.setOnAction(event ->
                    stage.close());
            Platform.runLater(() -> {
                SceneManager.getInstance().displayRoot(Root.MENU);
            });
            buttonStart.setOnAction(event -> {
                stage.close();
                Platform.runLater(() -> {
                    showBetStage(playersSelected);

                });

            });

            // Create and display the scene
            Scene scene = new Scene(anchorPane1, 300, 200);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            stage.setScene(scene);
            stage.showAndWait();

        });

        b2.setOnAction(e -> SceneManager.getInstance().displayRoot(Root.PROFILE));
        b3.setOnAction(e -> SceneManager.getInstance().displayRoot(Root.SETTINGS));
        b4.setOnAction(e -> System.exit(0));

        anchorPane.getChildren().add(vBox);
        anchorPane.setStyle(
                "-fx-background-image: url('/org/blackjack/view/immagine_iniziale.jpg');" +
                        "-fx-background-size: cover;"
        );
    }


    /**
     * Method that returns the main menu.
     *
     * @return the main menu
     */
    @Override
    public Parent getPane() {
        return anchorPane;
    }

    /**
     * Applies a specific style to a button.
     *
     * @param button The button to style.
     */
    public void styleButton(Button button) {

        button.setStyle(
                "-fx-background-color: #006400;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-family: 'Helvetica';"
        );
    }

    /**
     * Applies a specific style to a RadioButton.
     *
     * @param button The button to style.
     */
    public void customStyle(RadioButton button) {

        button.setStyle(
                "-fx-mark-color: gray;" +                 // Colore del cerchio interno selezionato
                        "-fx-font-size: 12px;" +                  // Dimensione del testo
                        "-fx-text-fill: white;" +                 // Colore del testo
                        "-fx-border-color: gray;" +             // Colore del bordo intorno al cerchio
                        "-fx-border-width: 2px;" +               // Larghezza del bordo
                        "-fx-border-radius: 50%;" +              // Bordo arrotondato (per cerchio)
                        "-fx-padding: 2 5 2 5;" +                    // Spaziatura interna
                        "-fx-focus-color: transparent;" +        // Rimuove il colore di focus
                        "-fx-hover-color: lightgray;"          // Colore del cerchio quando si passa sopra
        );
    }


    /**
     * This method manage the player's bet before the game starts.
     *
     * @return
     */
    public void showBetStage(int playersSelected) {
        Stage betStage = new Stage();
        betStage.initStyle(StageStyle.TRANSPARENT);
        betStage.setAlwaysOnTop(true);

        // Etichetta per il messaggio
        Label labelBet = new Label("Choose your bet:");
        labelBet.setStyle("-fx-font-size: 16px; -fx-font-family: 'Verdana'; -fx-text-fill: white;");

        // Pulsanti per le puntate
        RadioButton bet20 = new RadioButton("20");
        RadioButton bet50 = new RadioButton("50");
        RadioButton bet100 = new RadioButton("100");
        RadioButton bet200 = new RadioButton("200");

        // Stile per i pulsanti
        String radioButtonStyle = "-fx-mark-color: grey; -fx-text-fill: white; -fx-font-size: 14px;";
        bet20.setStyle(radioButtonStyle);
        bet50.setStyle(radioButtonStyle);
        bet100.setStyle(radioButtonStyle);
        bet200.setStyle(radioButtonStyle);

        // Gruppo per i pulsanti radio
        ToggleGroup betGroup = new ToggleGroup();
        bet20.setToggleGroup(betGroup);
        bet50.setToggleGroup(betGroup);
        bet100.setToggleGroup(betGroup);
        bet200.setToggleGroup(betGroup);

        // Selezione predefinita
        bet20.setSelected(true);

        // Listener per selezione
        betGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == bet20) {
                playerBet = 20;
            } else if (newValue == bet50) {
                playerBet = 50;
            } else if (newValue == bet100) {
                playerBet = 100;
            } else if (newValue == bet200) {
                playerBet = 200;
            }
        });

        // Pulsanti di azione
        Button buttonConfirm = new Button("Confirm");
        Button buttonCancel = new Button("Cancel");

        // Stile per i pulsanti
        String buttonStyle = " -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;";
        buttonConfirm.setStyle(buttonStyle);
        buttonCancel.setStyle("-fx-background-color: darkred; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        buttonConfirm.setOnAction(e -> {
            betStage.close();
            Platform.runLater(() -> {
                        SceneManager.getInstance().displayRoot(Root.GAME);
                        controller.startGame(playersSelected, "SISTEMAMI!");
                    }
            );
        });

        buttonCancel.setOnAction(e -> {
            playerBet = 0; // Imposta a 0 se l'utente annulla
            betStage.close(); //devo rimanere in menu no nel gioco
            Platform.runLater(() -> {
                SceneManager.getInstance().displayRoot(Root.MENU);
            });
        });

        // Layout pulsanti azione
        HBox actionButtons = new HBox(20, buttonConfirm, buttonCancel);
        actionButtons.setAlignment(Pos.CENTER);

        // Layout principale
        VBox mainBox = new VBox(20, labelBet, new HBox(10, bet20, bet50, bet100, bet200), actionButtons);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setStyle("-fx-background-color: #003100; -fx-padding: 20px; -fx-background-radius: 20;");

        // Imposta la scena e mostra
        Scene betScene = new Scene(mainBox, 300, 200);
        betScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        betStage.setScene(betScene);
        betStage.showAndWait();


    }

    public int getPlayerBet() {
        return playerBet;
    }
}
