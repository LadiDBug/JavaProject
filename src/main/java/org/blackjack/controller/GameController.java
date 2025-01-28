package org.blackjack.controller;


import org.blackjack.exception.GameOnGoingException;
import org.blackjack.model.BlackJackGame;
import org.blackjack.model.ComputerPlayer;
import org.blackjack.model.Player;
import org.blackjack.model.RealPlayer;
import org.blackjack.view.SceneManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final BlackJackGame game;
    private Thread gameThread;
    private final SceneManager sceneManager;
    private boolean play;
    private final String path = "src/main/data/userData.txt";

    public GameController() {

        this.game = new BlackJackGame();
        this.sceneManager = SceneManager.getInstance();
        game.addObserver(sceneManager);
        this.play = true;

    }


    public void startGame(int numerOfPlayers) {
        if (gameThread != null && gameThread.isAlive()) throw new GameOnGoingException("Thread still alive");
        gameThread = new Thread(() -> startGame1(numerOfPlayers));
        gameThread.start();
    }

    private void startGame1(int numberOfPlayers) {
        boolean play = true;

        try {
            game.setUpGame(numberOfPlayers);

            // System.out.println("Setup completato con successo");
        } catch (Exception e) {
            System.err.println("Errore durante setup: " + e.getMessage());
            e.printStackTrace();
            return; // Interrompi l'esecuzione se si verifica un'eccezione
        }

        while (play) {

            //Puntata del giocatore
            RealPlayer realPlayer = (RealPlayer) game.getPlayers().get(0);
            game.updateData(realPlayer);
            int bet = sceneManager.getBet();
            realPlayer.setBet(bet);  //setto la puntata
            realPlayer.toBet();  //tolgo le fiches al giocatore
            //System.out.println("Arrivo qui");
            game.drawInitialCards();

            //System.out.println("Arrivo qui2");
            handleTurn();

            sceneManager.showPlayAgain();
            sleep(1000);
            play = getIfPlayAgain();
            if (!play) {
                //se non vuole giocare piu esco dal while
                break;

            } else {
                //se vuole giocare ancora resetto il gioco
                game.resetGame();

            }
            //System.out.print("PArtita finita");


        }


        System.out.println("ciao");

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
        sleep(2000);


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

    private boolean getIfPlayAgain() {
        boolean play = sceneManager.getPlayAgain();
        while (!play) {
            try {
                Thread.sleep(100); // Piccola pausa per non sovraccaricare la CPU
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            play = sceneManager.getPlayAgain();
        }
        return play;
    }


    private void sleep(int milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public BlackJackGame getGame() {
        return game;
    }


    //Metodo per creare la prima volta il profilo con username e avatar
    public void createProfile() {
        sceneManager.createProfile(getData("Username"), getData("Avatar"));
    }

    public void createStats() {
        sceneManager.createStats(getData("TotalGames"), getData("TotalWins"), getData("TotalLosses"), getData("TotalFiches"));
    }

    public void createLevel() {
        sceneManager.createLevel(getData("Level"));
    }

    //Metodo per leggere i dati
    public String getData(String value) {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(value)) {
                    return line.split(" = ")[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
