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

        //Aggiungo elementi al profilePane
        profilePane.getChildren().addAll(backButton);

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


    public void createProfileBox(String username, String imagePath) {
        Label usernameLabel = new Label("Username: " + username);
        usernameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        Image profileImage = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setFitHeight(200);
        profileImageView.setFitWidth(200);

        VBox profileBox = new VBox(10, profileImageView, usernameLabel);
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setLayoutX(550);
        profileBox.setLayoutY(100);
        profilePane.getChildren().add(profileBox);

    }

    public void createStats(String totalGames, String totalWon, String totalLoses, String totalFiches) {
        Label games = new Label("Games played: " + totalGames);
        Label win = new Label("Total wins: " + totalWon);
        Label lose = new Label("Total losses: " + totalLoses);
        Label fiches = new Label("Fiches: " + totalFiches);

        games.setStyle("-fx-font-size: 20px; -fx-text-fill: white");
        win.setStyle("-fx-font-size: 20px; -fx-text-fill: white");
        lose.setStyle("-fx-font-size: 20px; -fx-text-fill: white");
        fiches.setStyle("-fx-font-size: 20px; -fx-text-fill: white");

        VBox statsBox = new VBox(10, games, win, lose, fiches);
        statsBox.setLayoutY(400);
        statsBox.setLayoutX(500);
        profilePane.getChildren().add(statsBox);
    }

    public void createLevel(String level) {
        Label liv = new Label("Level: " + level);
        liv.setStyle("-fx-font-size: 20px; -fx-text-fill: white");
        liv.setLayoutY(10);
        liv.setLayoutX(1100);
        //   HBox livBox = new HBox(10, liv);
        profilePane.getChildren().add(liv);

    }

    public void updateStats(int totalGames, int totalWon, int totalLosses, int totalFiches) {
        VBox stats = (VBox) profilePane.getChildren().get(1);

        Label games = (Label) stats.getChildren().get(0);
        games.setText("Games played: " + totalGames);
        Label win = (Label) stats.getChildren().get(1);
        win.setText("Total wins: " + totalWon);
        Label lose = (Label) stats.getChildren().get(2);
        lose.setText("Total losses: " + totalLosses);
        Label fiches = (Label) stats.getChildren().get(3);
        fiches.setText("Fiches: " + totalFiches);

    }

    public void updateLevel(int level) {
        Label liv = (Label) profilePane.getChildren().get(3);

        liv.setText("Level: " + level);

    }

    public void updateProfile(String username, String avatar) {
        VBox profile = (VBox) profilePane.getChildren().get(1);
        Label user = (Label) profile.getChildren().get(1);
        user.setText("Username: " + username);

        ImageView image = (ImageView) profile.getChildren().getFirst();
        Image im = new Image(getClass().getResource(avatar).toExternalForm());
        image.setImage(im);
    }


}
