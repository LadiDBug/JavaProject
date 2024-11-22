package org.blackjack.controller;


import org.blackjack.exception.GameOnGoingException;
import org.blackjack.model.BlackJackGame;
import org.blackjack.model.ComputerPlayer;
import org.blackjack.model.Player;
import org.blackjack.model.RealPlayer;
import org.blackjack.view.SceneManager;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private BlackJackGame game;
    private Thread gameThread;
    private SceneManager sceneManager;

    public GameController() {

        this.game = new BlackJackGame();
        this.sceneManager = SceneManager.getInstance();
        game.addObserver(sceneManager);
    }


    public void startGame(int numerOfPlayers, String playerName) {
        if (gameThread != null && gameThread.isAlive()) throw new GameOnGoingException("Thread still alive");
        gameThread = new Thread(() -> startGame1(numerOfPlayers, playerName));
        gameThread.start();
    }

    private void startGame1(int numberOfPlayers, String playerName) {
        game.setUpGame(numberOfPlayers, playerName);
        System.out.print("Game started");
        game.drawInitialCards();
        handleTurn();
    }


    public void handleTurn() {

        List<Player> players = new ArrayList<>(game.getPlayers());
        RealPlayer realPlayer = (RealPlayer) players.get(0);
        handleRealPlayerTurn(realPlayer);
        sleep(2000);


        for (int i = 1; i < players.size() - 1; i++) {
            handleComputerPlayerTurn((ComputerPlayer) players.get(i));
            sleep(2000);
        }

        sleep(2000);
        //dopo tutti i giocatori gioca il dealer
        game.dealerPlay();
        sleep(2000);
        //alla fine controllo chi vince
        game.checkWin(game.getPlayers(), game.getDealer());
    }

    public void handleRealPlayerTurn(RealPlayer player) {
        player.setstanding(false);

        if (game.checkBlackJack(player)) {
            player.setstanding(true);
            return;
        }
        while (!player.getstanding()) {
            int action = takeChoice();
            if (action == 1) {
                game.hit(player);
                game.checkBust(player);
                player.setstanding(true);
            } else if (action == 2) {
                game.stand(player);
                player.setstanding(true);
            }
            sceneManager.resetPlayerChoice();
        }


    }


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

    public int takeChoice() {
        int action = sceneManager.getPlayerAction();
        while (action == 0) { // Aspetta una scelta valida
            try {
                Thread.sleep(100); // Piccola pausa per non sovraccaricare la CPU
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            action = sceneManager.getPlayerAction();
        }
        return action;
    }


    private void sleep(int milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

/*

    public void checkWinners() {
        int dealerScore = game.getDealer().getScore();

        for (Player player : game.getPlayers()) {
            String result = game.checkWin(player, game.getDealer());

            if (player instanceof RealPlayer) {
                if (result.contains(player.getUsername())) {
                    ((RealPlayer) player).setTotalGames(((RealPlayer) player).getTotalGames() + 1);
                    ((RealPlayer) player).setWonGames(((RealPlayer) player).getWonGames() + 1);
                    ((RealPlayer) player).setTotalFiches(((RealPlayer) player).getTotalFiches() + ((RealPlayer) player).getBet() * 2);
                } else if (result.contains("Dealer")) {
                    ((RealPlayer) player).setTotalGames(((RealPlayer) player).getTotalGames() + 1);
                    ((RealPlayer) player).setLostGames(((RealPlayer) player).getLostGames() + 1);
                } else {
                    ((RealPlayer) player).setTotalGames(((RealPlayer) player).getTotalGames() + 1);
                }
            } else {
                // Gestire cosa fare quando controllo se vincono gli ai.
            }

        }
    }

*/

}