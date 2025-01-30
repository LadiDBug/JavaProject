package org.blackjack.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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


public class Profile implements WindowRoot {
    private final AnchorPane profilePane;
    private final GameController controller;
    private String avatarPath;

    public Profile() {

        profilePane = new AnchorPane();
        this.controller = new GameController();
        this.avatarPath = controller.getData("Avatar");

        //Bottone per tornare indiero
        Button backButton = createBackButton();
        backButton.setLayoutX(10);
        backButton.setLayoutY(10);

        Button modifyUsernameB = createButton("/org/blackjack/view/username.png");
        Button modifyAvatarB = createButton("/org/blackjack/view/avatar_button.png");
        addShadow(modifyUsernameB);
        addShadow(modifyAvatarB);

        HBox choice = new HBox(5, modifyUsernameB, modifyAvatarB);
        choice.setLayoutX(850);
        choice.setLayoutY(600);
        choice.setStyle("-fx-background-color: transparent");
        modifyUsernameB.setOnAction(e -> askUsername());
        modifyAvatarB.setOnAction(e -> askAvatar());

        VBox profileBox = createProfileBox();
        VBox statsBox = createStats();
        HBox levelBox = createLevel();

        //Aggiungo elementi al profilePane
        profilePane.getChildren().addAll(backButton, choice, profileBox, statsBox, levelBox);

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


    public VBox createProfileBox() {
        Label usernameLabel = new Label("Username: " + controller.getData("Username"));
        usernameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        Image profileImage = new Image(getClass().getResource(controller.getData("Avatar")).toExternalForm());
        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setFitHeight(200);
        profileImageView.setFitWidth(200);

        VBox profileBox = new VBox(10, profileImageView, usernameLabel);
        profileBox.setAlignment(Pos.CENTER);
        profileBox.setLayoutX(550);
        profileBox.setLayoutY(100);
        return profileBox;

    }

    public VBox createStats() {
        Label games = new Label("Games played: " + controller.getData("TotalGames"));
        Label win = new Label("Total wins: " + controller.getData("TotalWins"));
        Label lose = new Label("Total losses: " + controller.getData("TotalLosses"));
        Label fiches = new Label("Fiches: " + controller.getData("TotalFiches"));

        games.setStyle("-fx-font-size: 20px; -fx-text-fill: white");
        win.setStyle("-fx-font-size: 20px; -fx-text-fill: white");
        lose.setStyle("-fx-font-size: 20px; -fx-text-fill: white");
        fiches.setStyle("-fx-font-size: 20px; -fx-text-fill: white");

        VBox statsBox = new VBox(10, games, win, lose, fiches);
        statsBox.setLayoutY(400);
        statsBox.setLayoutX(500);
        return statsBox;
    }

    public HBox createLevel() {
        Label liv = new Label("Level: " + controller.getData("Level"));
        liv.setStyle("-fx-font-size: 20px; -fx-text-fill: white");

        HBox levelBox = new HBox(liv);
        levelBox.setAlignment(Pos.CENTER);
        levelBox.setStyle("-fx-background-color: darkgreen; -fx-background-radius: 10;");
        levelBox.setLayoutY(10);
        levelBox.setLayoutX(1150);
        return levelBox;

    }

    public void updateStats(int totalGames, int totalWon, int totalLosses, int totalFiches) {
        VBox stats = (VBox) profilePane.getChildren().get(3);
        System.out.println(totalGames);
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

        HBox levelBox = (HBox) profilePane.getChildren().get(4);
        Label liv = (Label) levelBox.getChildren().get(0);
        liv.setText("Level: " + level);

    }

    public void updateProfile(String username, String avatar) {
        VBox profile = (VBox) profilePane.getChildren().get(2);

        Label usernameLabel = (Label) profile.getChildren().get(1);
        usernameLabel.setText("Username: " + username);

        Image profileImage = new Image(getClass().getResource(avatar).toExternalForm());
        ImageView profileImageView = (ImageView) profile.getChildren().get(0);
        profileImageView.setImage(profileImage);

    }

    private void modifyProfile(String key, String value) {
        if (key == "Username") {
            controller.replaceData("Username", value);
            updateProfile(value, avatarPath);
        } else {
            controller.replaceData("Avatar", value);
            updateProfile(controller.getData("Username"), value);
        }
    }


    private Button createButton(String imagePath) {
        Button button = new Button();
        button.setStyle("-fx-background-color: transparent");
        Image icon = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(70);
        iconView.setFitWidth(200);
        button.setGraphic(iconView);
        return button;
    }

    private void askUsername() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);

        Label ask = new Label("Insert username");
        ask.setStyle("-fx-font-size: 14px; -fx-font-family: Verdana; -fx-text-fill: white;");
        ask.setAlignment(Pos.CENTER);

        TextField username = new TextField();
        Button confirm = new Button("Confirm");
        Button cancel = new Button("Cancel");
        addShadow(confirm);
        addShadow(cancel);
        confirm.setStyle("-fx-background-colodr: #FFD700; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;"); // Oro
        cancel.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        confirm.setOnAction(e -> {
            modifyProfile("Username", username.getText());
            stage.close();
        });

        cancel.setOnAction(e -> stage.close());
        HBox confirmBox = new HBox(10, confirm, cancel);

        VBox box = new VBox(10.0, ask, username, confirmBox);
        box.setStyle(
                "-fx-background-color:#003100;" +
                        "-fx-background-position: center;" +
                        "-fx-padding: 20px; " +
                        "fx-border-radius: 20;" +
                        "-fx-background-radius: 20;"
        );
        box.setAlignment(Pos.CENTER);

        Scene scene = new Scene(box, 250, 150);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void askAvatar() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);

        Label ask = new Label("Choose your avatar");
        ask.setStyle("-fx-font-size: 14px; -fx-font-family: Verdana; -fx-text-fill: white;");
        ask.setAlignment(Pos.CENTER);

        Button confirm = new Button("Confirm");
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> stage.close());
        confirm.setOnAction(e -> {
            stage.close();
            modifyProfile("Avatar", avatarPath);
        });
        addShadow(confirm);
        addShadow(cancel);
        confirm.setStyle("-fx-background-colodr: #FFD700; -fx-text-fill: black; -fx-font-size: 14px; -fx-font-weight: bold;"); // Oro
        cancel.setStyle("-fx-background-color: #8B0000; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

        Button a1 = createButtonAvatar("/org/blackjack/view/avatars/girl.png");
        Button a2 = createButtonAvatar("/org/blackjack/view/avatars/blonde_girl.png");
        Button a3 = createButtonAvatar("/org/blackjack/view/avatars/gamer.png");
        Button a4 = createButtonAvatar("/org/blackjack/view/avatars/man.png");
        a1.setOnAction(e -> avatarPath = "/org/blackjack/view/avatars/girl.png");
        a2.setOnAction(e -> avatarPath = "/org/blackjack/view/avatars/blonde_girl.png");
        a3.setOnAction(e -> avatarPath = "/org/blackjack/view/avatars/gamer.png");
        a4.setOnAction(e -> avatarPath = "/org/blackjack/view/avatars/man.png");

        addShadow(a1);
        addShadow(a2);
        addShadow(a3);
        addShadow(a4);

        HBox b1 = new HBox(10, a1, a2, a3, a4);

        HBox confirmBox = new HBox(10, confirm, cancel);
        confirmBox.setAlignment(Pos.CENTER);

        VBox box = new VBox(20.0, ask, b1, confirmBox);
        box.setStyle(
                "-fx-background-color:#003100;" +
                        "-fx-background-position: center;" +
                        "-fx-padding: 20px; " +
                        "fx-border-radius: 20;" +
                        "-fx-background-radius: 20;"
        );
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box, 500, 220);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();

    }

    private Button createButtonAvatar(String imagePath) {
        Button button = new Button();
        button.setStyle("-fx-background-color: transparent");
        Image icon = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(100);
        iconView.setFitWidth(100);
        button.setGraphic(iconView);
        return button;
    }

    private void addShadow(Button b) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.LIGHTGRAY);
        b.setOnMouseEntered(e -> b.setEffect(shadow));
        b.setOnMouseExited(e -> b.setEffect(null));

    }
}
