package org.blackjack.controller;


import org.blackjack.exception.GameOnGoingException;
import org.blackjack.model.BlackJackGame;
import org.blackjack.model.RealPlayer;
import org.blackjack.view.SceneManager;


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

    public void waitGame() {
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void handleTurn() {
        game.getPlayers().forEach(player -> {
            if (player instanceof RealPlayer) {
                handleRealPlayerTurn((RealPlayer) player);
            } else {
                // handleComputerPlayerTurn((ComputerPlayer) player);
            }
        });

        //dopo tutti i giocatori gioca il dealer
        dealerPlay();

        //alla fine controllo chi vince
        checkWinners();
    }

    public void handleRealPlayerTurn(RealPlayer player) {
        boolean continueTurn = true;
        while (continueTurn) {
            int action = takeChoice();     //COSA METTO?

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

    public int takeChoice() {
        return sceneManager.getPlayerAction();
    }

    public void playerBust(RealPlayer player) {
        //TODO: Implementare cosa fare quando un player busta
        System.out.println("Ha fatto bust");
    }

    public void playerSplit(RealPlayer player) {
        //TODO: implementare cosa fare quando un player split
        System.out.println("Ha fatto split");
    }

    public void dealerPlay() {
        //TODO: implementare cosa fare quando gioca il dealer
        System.out.println("Il dealer gioca");
    }

    public void checkWinners() {
        //TODO: implementare cosa fare quando controllo chi vince
        System.out.println("Controllo chi vince");
    }
   /* public void handleComputerPlayerTurn(ComputerPlayer player) {
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



}
*/

}