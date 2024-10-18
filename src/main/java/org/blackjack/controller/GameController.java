package org.blackjack.controller;


import org.blackjack.exception.GameOnGoingException;
import org.blackjack.model.BlackJackGame;
import org.blackjack.model.ComputerPlayer;
import org.blackjack.model.Player;
import org.blackjack.model.RealPlayer;

public class GameController {

    private BlackJackGame game;
    private Thread gameThread;

    public GameController() {
        this.game = new BlackJackGame();
    }

    private void startGame1() {
        String playerName = "Mario";
        int numberOfPlayers = 2;

        game.setUpGame(numberOfPlayers, playerName);

        handleTurn();
    }

    public void startGame() {
        if (gameThread != null && gameThread.isAlive()) throw new GameOnGoingException("Thread still alive");
        gameThread = new Thread(this::startGame1);
        gameThread.start();
    }

    public void waitGame() {
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void handleTurn() {
        boolean continuePlaying = true;    //flag per continuare la partita

        while (continuePlaying) {
            placeBet();

            game.drawInitialCards();

            playGame();

            continuePlaying = false;
            if (continuePlaying) {
                game.resetGame();
            }
        }
        game.quitGame();
    }

    public void placeBet() {
        for (Player player : game.getPlayers()) {
            if (player instanceof RealPlayer) {
                int bet = 50;

                RealPlayer realPlayer = (RealPlayer) player;
                realPlayer.setBet(bet);
                realPlayer.setTotalFiches(realPlayer.getTotalFiches() - bet);
            }
        }
    }


    public void playGame() {
        for (Player player : game.getPlayers()) {
            if (player instanceof RealPlayer) {
                handleRealPlayerTurn((RealPlayer) player);
            } else {
                handleComputerPlayerTurn((ComputerPlayer) player);
            }
        }

        //dopo tutti i giocatori gioca il dealer
        dealerPlay();

        //alla fine controllo chi vince
        checkWinners();
    }


    public void handleRealPlayerTurn(RealPlayer player) {
        boolean continueTurn = true;
        while (continueTurn) {
            int action = 1;

            switch (action) {
                case 1: //HIT
                    player.hit(game.getDeck().drawCard());
                    if (game.checkBust(player)) {
                        playerBust(player);
                        continueTurn = false;
                    }
                    break;
                case 2: //STAND
                    continueTurn = false;
                    return;
                case 3: //DOUBLE DOWN
                    player.setBet(player.getBet() * 2);
                    player.hit(game.getDeck().drawCard());
                    continueTurn = false;
                    break;
                case 4:
                    playerSplit(player);
            }
        }
    }

    public void handleComputerPlayerTurn(ComputerPlayer player) {
        player.setstanding(false);

        if (game.checkBlackJack(player)) {
            //display una scritta che ha fatto blackJack
        }

        while (!player.getstanding()) {
            int currentScore = player.getScore();
            if (currentScore <= 15) {
                player.hit(game.getDeck().drawCard());
            } else {
                player.stand();
            }
        }

        if (game.checkBust(player)) {
            //Display una scritta che il player bust
            return;
        }


    }

    public void dealerPlay() {
        game.getDealer().setstanding(false);

        while (!game.getDealer().getstanding()) {
            int dealerScore = game.getDealer().getScore();

            if (dealerScore < 17) {
                game.getDealer().hit(game.getDeck().drawCard());
            } else {
                game.getDealer().stand();
            }
        }
    }


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

    public void playerSplit(RealPlayer player) {
    }

    public void playerBust(RealPlayer player) {
    }


}