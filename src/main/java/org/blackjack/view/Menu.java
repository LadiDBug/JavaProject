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

public class Menu implements WindowRoot {

    private final AnchorPane anchorPane;
    private final GameController controller;
    private int playersSelected = 1;

    public Menu() {
        this.controller = new GameController();
        anchorPane = new AnchorPane();
        Button b1 = new Button("Gioca");
        Button b2 = new Button("Profilo");
        Button b3 = new Button("Impostazioni");
        Button b4 = new Button("Esci");

        VBox vBox = new VBox(b1, b2, b3, b4);
        vBox.setStyle("-fx-background-color: transparent");
        vBox.setPrefWidth(200);
        vBox.setPrefHeight(400);

        vBox.setLayoutX(540);
        vBox.setLayoutY(100);

        vBox.setAlignment(Pos.CENTER);


        vBox.setSpacing(20);

        //stile dei bottoni
        styleButton(b1);
        styleButton(b2);
        styleButton(b3);
        styleButton(b4);

        //Azioni sui bottoni
        b1.setOnAction(e -> {
            Stage stage = new Stage();
            AnchorPane anchorPane1 = new AnchorPane();

            RadioButton onePlayer = new RadioButton("1");
            RadioButton twoPlayer = new RadioButton("2");
            RadioButton threePlayer = new RadioButton("3");
            RadioButton fourPlayer = new RadioButton("4");

            ToggleGroup numberOfPlayers = new ToggleGroup();
            onePlayer.setToggleGroup(numberOfPlayers);
            twoPlayer.setToggleGroup(numberOfPlayers);
            threePlayer.setToggleGroup(numberOfPlayers);
            fourPlayer.setToggleGroup(numberOfPlayers);

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

            Button buttonStart = new Button("Inizia");
            Button buttonExit = new Button("Annulla");

            Label labelAskNumberPlayers = new Label("Scegli il numero di giocatori della partita: ");

            HBox choicePlayers = new HBox(onePlayer, twoPlayer, threePlayer, fourPlayer);
            HBox startExitBox = new HBox(labelAskNumberPlayers, buttonStart, buttonExit);
            VBox box2 = new VBox(choicePlayers, startExitBox);

            anchorPane1.getChildren().add(box2);
            buttonExit.setOnAction(event -> stage.close());
            buttonStart.setOnAction(event -> {
                stage.close();
                SceneManager.getInstance().displayRoot(Root.GAME);
                controller.startGame(playersSelected, "Mario");
                System.out.println("HA SCELTO:" + playersSelected);
            });

            //vbox due hbox bottoni e startannulla
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


    @Override
    public Parent getPane() {
        return anchorPane;
    }

    public void styleButton(Button button) {

        button.setStyle(
                "-fx-background-color: #006400;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-family: 'Helvetica';"
        );
    }
}
