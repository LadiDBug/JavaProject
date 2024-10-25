package org.blackjack.view;

import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;


public class Profile implements WindowRoot {
    private final AnchorPane profilePane;


    public Profile() {
        profilePane = new AnchorPane();
    }

    @Override
    public Parent getPane() {
        return profilePane;
    }

}
