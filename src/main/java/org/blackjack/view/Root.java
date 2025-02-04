package org.blackjack.view;

import javafx.scene.Parent;

/**
 * This is an enum class that contains all the root windows of the application.
 * Each root window is associated with a WindowRoot object.
 * The enum class is used to get the root window object and the root window pane.
 * The root window pane is used to get the root window scene.
 *
 * @author Diana Pamfile
 */
public enum Root {

    MENU(new Menu()),
    PROFILE(new Profile()),
    GAME(new Game());


    private final WindowRoot windowRoot;

    Root(WindowRoot windowRoot) {
        this.windowRoot = windowRoot;
    }

    public WindowRoot getWindowRoot() {
        return windowRoot;
    }

    public Parent getPane() {
        return windowRoot.getPane();
    }
}
