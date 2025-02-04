package org.blackjack.controller;


import org.blackjack.exception.GameOnGoingException;
import org.blackjack.model.BlackJackGame;
import org.blackjack.model.ComputerPlayer;
import org.blackjack.model.Player;
import org.blackjack.model.RealPlayer;
import org.blackjack.view.SceneManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the controller of the game. It manages the game flow and the interaction between the model and the view.
 * It also manages the game thread.
 *
 * @author Diana Pamfile
 */
public class GameController {

    /**
     * The game instance, the model part of the MVC pattern.
     */
    private final BlackJackGame game;

    /**
     * The thread that runs the game.
     */
    private Thread gameThread;

    /**
     * The scene manager instance, the view part of the MVC pattern.
     */
    private final SceneManager sceneManager;

    /**
     * The play variable that is used to check if the player wants to play again.
     */
    private boolean play;

    /**
     * The path of the file where the data is stored.
     */
    private final String path = "src/main/data/userData.txt";

    /**
     * Constructor of the GameController class. It initializes the game and the scene manager.
     * It also sets the play variable to true.
     */
    public GameController() {

        this.game = new BlackJackGame();
        this.sceneManager = SceneManager.getInstance();
        game.addObserver(sceneManager);
        this.play = true;

    }

    /**
     * Method that starts the game. It creates a new thread and calls the startGame1 method.
     *
     * @param numerOfPlayers
     */
    public void startGame(int numerOfPlayers) {
        if (gameThread != null && gameThread.isAlive()) throw new GameOnGoingException("Thread still alive");
        gameThread = new Thread(() -> startGame1(numerOfPlayers));
        gameThread.start();
    }

    /**
     * This method starts the game. It sets up the game and then it enters a loop where it handles the turns of the players.
     * The game ends when the player decides to stop playing.
     *
     * @param numberOfPlayers
     */
    private void startGame1(int numberOfPlayers) {
        boolean play = true;

        try {
            game.setUpGame(numberOfPlayers);

        } catch (Exception e) {
            System.err.println("Errore durante setup: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        while (play) {

            RealPlayer realPlayer = (RealPlayer) game.getPlayers().get(0);

            int bet = sceneManager.getBet();
            realPlayer.setBet(bet);
            realPlayer.toBet();
            game.drawInitialCards();
            handleTurn();
            game.updateData(realPlayer);
            sceneManager.showPlayAgain();
            sleep(1000);
            play = getIfPlayAgain();

            if (!play) {
                break;

            } else {
                game.resetGame();

            }
        }


    }

    /**
     * Method that handles the turn of the players. It starts with the real player and then it handles the computer players.
     * It also handles the dealer's turn.
     * In the end it checks who won and who lose.
     */
    public void handleTurn() {

        List<Player> players = new ArrayList<>(game.getPlayers());
        RealPlayer realPlayer = (RealPlayer) players.get(0);
        handleRealPlayerTurn(realPlayer);
        sleep(1500);


        for (int i = 1; i < players.size(); i++) {
            handleComputerPlayerTurn((ComputerPlayer) players.get(i));
            sleep(1000);

        }

        //dopo tutti i giocatori gioca il dealer
        sceneManager.removeHiddenCard(game.getDealer().getScore());
        sleep(1000);
        game.dealerPlay();
        sleep(2000);

        game.checkWin(game.getPlayers(), game.getDealer());
        sleep(2000);


    }

    /**
     * This method handles the turn of the real player.
     * It checks if the player has a blackjack and if he does, it increases the number of won games and sets the standing variable to true.
     * It then enters a loop where it waits for the player to choose between hitting and standing.
     * If the player chooses to hit, it draws a card for the player and checks if the player has busted.
     * If the player chooses to stand, it sets the standing variable to true.
     *
     * @param player
     */
    public void handleRealPlayerTurn(RealPlayer player) {
        player.setstanding(false);

        if (game.checkBlackJack(player)) {
            player.increaseWonGames();
            player.setstanding(true);
            return;
        }
        while (!player.getstanding()) {
            int action = takeChoice();
            if (action == 1) {
                game.hit(player);
                if (game.checkBust(player)) {
                    player.increaseLostGames();
                    player.setstanding(true);
                }

            } else if (action == 2) {
                game.stand(player);
                player.setstanding(true);
            }
            sceneManager.resetPlayerChoice();
        }


    }


    /**
     * This method handles the turn of the computer player.
     * It checks if the player has a blackjack and if he does, it sets the standing variable to true.
     * It then enters a loop where it checks the score of the player and if it is less than 15, it draws a card for the player.
     * If the score is greater than 15, it sets the standing variable to true.
     *
     * @param player
     */
    public void handleComputerPlayerTurn(ComputerPlayer player) {
        player.setstanding(false);

        if (game.checkBlackJack(player)) {
            player.setstanding(true);
            return;
        }

        while (!player.getstanding()) {
            int currentScore = player.getScore();
            if (currentScore <= 15) {
                game.hit(player);
            } else {
                player.setstanding(true);
            }
        }

        game.checkBust(player);


    }

    /**
     * This method waits for the player to choose between hitting or standing.
     *
     * @return
     */
    public int takeChoice() {
        int action = sceneManager.getPlayerAction();
        while (action == 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            action = sceneManager.getPlayerAction();
        }
        return action;
    }

    /**
     * This method waits for the player to choose if he wants to play again.
     *
     * @return
     */
    private boolean getIfPlayAgain() {
        boolean play = sceneManager.getPlayAgain();
        while (!play) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            play = sceneManager.getPlayAgain();
        }
        return play;
    }


    /**
     * This method puts the thread to sleep for a certain amount of time.
     *
     * @param milliSec
     */
    private void sleep(int milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * This method returns the game.
     *
     * @return
     */
    public BlackJackGame getGame() {
        return game;
    }


    /**
     * This method reads the data from the file.
     *
     * @param key
     * @return
     */
    public String getData(String key) {
        return game.readData(key);
    }

    /**
     * This method writes the data to the file.
     *
     * @param key
     * @param newValue
     */
    public void replaceData(String key, String newValue) {
        game.replaceData(key, newValue);
    }


}
