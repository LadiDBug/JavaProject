package org.blackjack.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Menu implements WindowRoot {

    private final AnchorPane anchorPane;

    public Menu() {
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

        // b1.setOnAction((ActionEvent e) -> SceneManager.getInstance().displayRoot(Root.GAME));
        //TODO idem per b2-b4
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
