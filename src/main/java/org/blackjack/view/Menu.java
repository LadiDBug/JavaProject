package org.blackjack.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.blackjack.controller.GameController;

/**
 * This class represents the main menu of the game.
 * Allows access to the sections of the game, such as Game, Profile, Settings and Exit
 *
 * @Author Diana Pamfile
 */
public class Menu implements WindowRoot {

    private final AnchorPane anchorPane;
    private final GameController controller;
    private int playersSelected = 1;
    private int playerBet = 20;


    /**
     * Constructor that initializes the main menu.
     */
    public Menu() {
        this.controller = new GameController();

        anchorPane = new AnchorPane();

        // Menu Buttons
        Button b1 = createButton("/org/blackjack/view/menuButton/play_button.png", e -> preGameQuestion());
        Button b2 = createButton("/org/blackjack/view/menuButton/profile_button.png", e -> {
            SceneManager.getInstance().displayRoot(Root.PROFILE);

        });
        Button b3 = createButton("/org/blackjack/view/menuButton/settings_button.png", e -> settingsDialog());
        Button b4 = createButton("/org/blackjack/view/menuButton/exit_button.png", e -> System.exit(0));


        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.LIGHTYELLOW);
        b1.setOnMouseEntered(e -> b1.setEffect(shadow));
        b1.setOnMouseExited(e -> b1.setEffect(null));
        b2.setOnMouseEntered(e -> b2.setEffect(shadow));
        b2.setOnMouseExited(e -> b2.setEffect(null));
        b3.setOnMouseEntered(e -> b3.setEffect(shadow));
        b3.setOnMouseExited(e -> b3.setEffect(null));
        b4.setOnMouseEntered(e -> b4.setEffect(shadow));
        b4.setOnMouseExited(e -> b4.setEffect(null));

        // Vbox for the buttons
        HBox hBox = new HBox(b1, b2, b3, b4);
        hBox.setStyle("-fx-background-color: transparent");
        hBox.setPrefWidth(600);
        hBox.setPrefHeight(100);
        hBox.setLayoutX(200);
        hBox.setLayoutY(90);
        hBox.setAlignment(Pos.CENTER);

        anchorPane.getChildren().add(hBox);
        anchorPane.setStyle(
                "-fx-background-image: url('/org/blackjack/view/sfondo_c.jpg');" +
                        "-fx-background-size: cover;"
        );

    }

    /**
     * Method that creates the buttons of the menu, with an image and an action.
     *
     * @param imagePath the path of the image used for the button.
     * @param action    the action associated with the button.
     * @return the button already created.
     */
    private Button createButton(String imagePath, EventHandler<ActionEvent> action) {
        Button button = new Button();
        button.setStyle("-fx-background-color: trasparent");

        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(70);
        button.setGraphic(imageView);

        button.setOnAction(action);
        return button;
    }


    /**
     * This method asks the user how many players will play the game.
     * The user can choose between 1, 2, 3 or 4 players.
     */
    private void preGameQuestion() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);
        stage.initModality(Modality.APPLICATION_MODAL);


        // Radio button for the choice of the number of players
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

        // set default selection, very important because the user can not continue without selecting a number of players
        onePlayer.setSelected(true);

        // Listener for the selection of the number of players
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
        Button buttonStart = new Button("Continue");
        Button buttonExit = new Button("Cancel");
        buttonStart.setStyle("-fx-background-colodr: #FFD700; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;"); // Oro
        buttonExit.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;"); // Rosso scuro


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

        buttonExit.setOnAction(event -> {
            stage.close();
            resetPlayerSelected();
        });

        buttonStart.setOnAction(event -> {
            stage.close();
            Platform.runLater(() -> {
                showBetStage();

            });

        });

        // Create and display the scene
        Scene scene = new Scene(box2, 300, 250);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();

    }


    /**
     * Method that returns the menuPane.
     *
     * @return the main menu
     */
    @Override
    public Parent getPane() {
        return anchorPane;
    }


    /**
     * Applies a specific style to the radio button.
     * This method is used for the radio buttons that allow the user to choose the number of players.
     *
     * @param button The button to style.
     */
    private void customStyle(RadioButton button) {

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
     * This method shows the bet stage and allows the user to choose the bet.
     * The user can choose between 20, 50, 100 or 200.
     */
    public void showBetStage() {
        Stage betStage = new Stage();
        betStage.initStyle(StageStyle.TRANSPARENT);
        betStage.setAlwaysOnTop(true);
        betStage.initModality(Modality.APPLICATION_MODAL);

        Label labelBet = new Label("Choose your bet:");
        labelBet.setStyle("-fx-font-size: 16px; -fx-font-family: 'Verdana'; -fx-text-fill: white;");


        RadioButton bet20 = new RadioButton("20");
        RadioButton bet50 = new RadioButton("50");
        RadioButton bet100 = new RadioButton("100");
        RadioButton bet200 = new RadioButton("200");


        String radioButtonStyle = "-fx-mark-color: grey; -fx-text-fill: white; -fx-font-size: 14px;";
        bet20.setStyle(radioButtonStyle);
        bet50.setStyle(radioButtonStyle);
        bet100.setStyle(radioButtonStyle);
        bet200.setStyle(radioButtonStyle);


        ToggleGroup betGroup = new ToggleGroup();
        bet20.setToggleGroup(betGroup);
        bet50.setToggleGroup(betGroup);
        bet100.setToggleGroup(betGroup);
        bet200.setToggleGroup(betGroup);


        bet20.setSelected(true);


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


        Button buttonConfirm = new Button("Confirm");
        Button buttonCancel = new Button("Cancel");


        String buttonStyle = " -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;";
        buttonConfirm.setStyle(buttonStyle);
        buttonCancel.setStyle("-fx-background-color: darkred; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        buttonConfirm.setOnAction(e -> {
            betStage.close();
            Platform.runLater(() -> {
                        SceneManager.getInstance().displayRoot(Root.GAME);
                        System.out.println("Player selected: " + playersSelected);
                        System.out.println("Player bet: " + playerBet);
                        controller.startGame(playersSelected);
                    }
            );
        });

        buttonCancel.setOnAction(e -> {
            playerBet = 20; // Imposta a 0 se l'utente annulla
            betStage.close(); //devo rimanere in menu e non nel gioco
            resetPlayerSelected();
            Platform.runLater(() -> {
                SceneManager.getInstance().displayRoot(Root.MENU);
            });
        });


        HBox actionButtons = new HBox(20, buttonConfirm, buttonCancel);
        actionButtons.setAlignment(Pos.CENTER);


        VBox mainBox = new VBox(20, labelBet, new HBox(10, bet20, bet50, bet100, bet200), actionButtons);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setStyle("-fx-background-color: #003100; -fx-padding: 20px; -fx-background-radius: 20;");


        Scene betScene = new Scene(mainBox, 300, 200);
        betScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        betStage.setScene(betScene);
        betStage.showAndWait();


    }

    /**
     * Method that resets the number of players in the game.
     */
    public void resetPlayerSelected() {
        this.playersSelected = 1;
        // System.out.println("Ho resettato i giocatori" + playersSelected);
    }

    /**
     * Getter method that return the bet of the player.
     *
     * @return
     */
    public int getPlayerBet() {
        return playerBet;
    }

    /**
     * This method shows the setting dialog.
     * The user can change the volume of the background music, and enable or disable the sound effects.
     */
    public void settingsDialog() {
        Stage settingStage = new Stage();
        settingStage.initStyle(StageStyle.TRANSPARENT);
        settingStage.setAlwaysOnTop(true);
        settingStage.initModality(Modality.APPLICATION_MODAL);

        Label settings = new Label("Settings");
        settings.setStyle("-fx-font-size: 22px; -fx-font-family: 'Verdana'; -fx-text-fill: white;");

        Label settingVol = new Label("Volume: ");
        settingVol.setStyle("-fx-font-size: 18px; -fx-font-fx-family: 'Verdana'; -fx-text-fill: white;");

        Button close = new Button("Close");
        close.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        Slider volumeSlider = new Slider();
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setBlockIncrement(5);
        volumeSlider.setValue(50);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue() / 100;
            SceneManager.getInstance().setVolume(volume);
        });

        HBox volumeBox = new HBox(10, settingVol, volumeSlider);
        volumeBox.setAlignment(Pos.CENTER_LEFT);

        ToggleButton effects = new ToggleButton("Yes");
        effects.setSelected(true);
        effects.setOnAction(e -> {
            if (effects.isSelected()) {
                SceneManager.getInstance().setEffects(true);
                effects.setText("Yes");
            } else {
                SceneManager.getInstance().setEffects(false);
                effects.setText("No");
            }
        });


        Label effectLabel = new Label("Sound Effects: ");
        effectLabel.setStyle("-fx-font-size: 16px; -fx-font-family: 'Verdana'; -fx-text-fill: white;");
        HBox effectBox = new HBox(20, effectLabel, effects);


        VBox box = new VBox(20, settings, volumeBox, effectBox, close);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color:#003100;" +
                "-fx-background-position: center;" +
                "-fx-padding: 20px; " +
                "fx-border-radius: 20;" +
                "-fx-background-radius: 20;");

        close.setOnAction(e -> settingStage.close());
        Scene setScene = new Scene(box, 300, 200);
        setScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        settingStage.setScene(setScene);
        settingStage.showAndWait();


    }
}
