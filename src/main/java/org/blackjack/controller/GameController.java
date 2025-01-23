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

    private final BlackJackGame game;
    private Thread gameThread;
    private final SceneManager sceneManager;

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
        try {
            game.setUpGame(numberOfPlayers, playerName);
            // System.out.println("Setup completato con successo");
        } catch (Exception e) {
            System.err.println("Errore durante setup: " + e.getMessage());
            e.printStackTrace();
            return; // Interrompi l'esecuzione se si verifica un'eccezione
        }


        //Puntata del giocatore
        RealPlayer realPlayer = (RealPlayer) game.getPlayers().get(0);
        int bet = sceneManager.getBet();
        realPlayer.setBet(bet);  //setto la puntata
        realPlayer.toBet();  //tolgo le fiches al giocatore
        //System.out.println("Arrivo qui");
        game.drawInitialCards();
        //System.out.println("Arrivo qui2");
        handleTurn();

    }


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

    }

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


}