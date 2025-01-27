package org.blackjack.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Profile implements WindowRoot {
    private final AnchorPane profilePane;


    public Profile() {
        profilePane = new AnchorPane();

        //Bottone per tornare indiero
        Button backButton = createBackButton();
        backButton.setLayoutX(10);
        backButton.setLayoutY(10);

        // Box per l'immagine del profilo e username
        VBox profileBox = createProfileBox();

        // Hbox per la parola statistiche

        // Box per statistiche
        VBox statsBox = createStatsBox();

        //Box per il livello
        HBox levelBox = createLevelBox();

        //posizionali sullo schermo
        profileBox.setLayoutX(520);
        profileBox.setLayoutY(100);

        statsBox.setLayoutX(520);
        statsBox.setLayoutY(400);

        levelBox.setLayoutX(1100);
        levelBox.setLayoutY(10);
        //Aggiungo elementi al profilePane
        profilePane.getChildren().addAll(backButton, profileBox, statsBox, levelBox);

        profilePane.setStyle("-fx-background-image: url('/org/blackjack/view/sfondo_profilo.jpg');" +
                "-fx-background-size: cover;");
    }

    @Override
    public Parent getPane() {
        return profilePane;
    }

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

    private VBox createProfileBox() {
        Label username = new Label("Username: " + "nomeSuo");
        username.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        username.setAlignment(Pos.CENTER);

        Image profileImage = new Image(getClass().getResource("defaultUser.png").toExternalForm());
        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setFitHeight(200);
        profileImageView.setFitWidth(200);

        return new VBox(10, profileImageView, username);
    }

    private VBox createStatsBox() {

        Label gamesPlayed = new Label("Games played: " + "totalGamesSuo");
        gamesPlayed.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        Label gamesWon = new Label("Games won: " + "gamesWonSuo");
        gamesWon.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        Label gamesLost = new Label("Games lost: " + "gamesLostSuo");
        gamesLost.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        Label totalFiches = new Label("Total fiches: " + "totalFichesSuo");
        totalFiches.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        return new VBox(10, gamesPlayed, gamesWon, gamesLost);
    }

    private HBox createLevelBox() {
        Label level = new Label("Level: " + "levelSuo");
        level.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        return new HBox(10, level);
    }
}
