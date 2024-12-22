package org.blackjack.view;

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
import org.blackjack.controller.GameController;

/**
 * This class represents the main menu of the game.
 * Allows access to the sections of the game, such as Game, Profile, Settings and Exit
 */
public class Menu implements WindowRoot {

    private final AnchorPane anchorPane;
    private final GameController controller;
    private int playersSelected = 1;

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
                            "fx-border-radius: 10;" +
                            "-fx-background-radius: 10;"
            );
            box2.setPrefSize(300, 200);

            anchorPane1.getChildren().add(box2);

            buttonExit.setOnAction(event -> stage.close());
            buttonStart.setOnAction(event -> {
                stage.close();
                SceneManager.getInstance().displayRoot(Root.GAME);
                //TODO: Sistema nome giocatore
                controller.startGame(playersSelected, "SISTEMAMI!");
                System.out.println("HA SCELTO:" + playersSelected);
            });

            // Create and display the scene
            Scene scene = new Scene(anchorPane1, 300, 200);
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
}
