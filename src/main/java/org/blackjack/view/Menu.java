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
        vBox.setStyle("-fx-background-color: green");
        vBox.setPrefWidth(200);
        vBox.setPrefHeight(400);

        vBox.setLayoutX(540);
        //TODO: set.LayoutY

        vBox.setAlignment(Pos.CENTER);

        //TODO: spazio tra bottoni in vbox
        // TODO: stile bottoni in button


        // b1.setOnAction((ActionEvent e) -> SceneManager.getInstance().displayRoot(Root.GAME));
        //TODO idem per b2-b4
        anchorPane.getChildren().add(vBox);

    }


    @Override
    public Parent getPane() {
        return anchorPane;
    }
}
