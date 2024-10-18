package org.blackjack.view;

import javafx.scene.Parent;

public enum Root {

    MENU(new Menu()),
    PROFILE(new Profile()),
    SETTINGS(new Settings()),
    GAME(new Game());


    private final WindowRoot windowRoot;

    Root(WindowRoot windowRoot) {
        this.windowRoot = windowRoot;
    }

    public WindowRoot getGame() {
        return windowRoot;
    }

    public Parent getPane() {
        return windowRoot.getPane();
    }
}
