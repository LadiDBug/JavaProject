package org.blackjack.model;

import org.blackjack.api.*;
import org.blackjack.view.TypePlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.stream.IntStream;

/**
 * This class represents the BlackJack game.
 * It implements the interaction between the players and the dealer.
 * It also manages the deck of cards.
 * It extends the Observable class to notify the observers (the view) of the changes in the game.
 */
public class BlackJackGame extends Observable {
    private List<Player> players;
    private Dealer dealer;
    private Deck deck;

    private static final String[] USERNAMES = {"Mario", "Michelangelo", "Batman"};
    private static final String[] AVATARS = {
            "/org/blackjack/view/super-mario.png",
            "/org/blackjack/view/michaelangelo.png",
            "/org/blackjack/view/batman.png"
    };


    /**
     * Constructor of the BlackJackGame class.
     */
    public BlackJackGame() {
        players = new ArrayList<>();
        deck = Deck.getInstance();
        dealer = new Dealer();
    }

    //Getters
    public List<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public Dealer getDealer() {
        return dealer;
    }

    /*
     * This method sets up the game by adding the players to the game.
     *
     * @param numberOfPlayers the number of players in the game
     * @param usernameRealPlayer the username of the real player
     */
    public void setUpGame(int numberOfPlayers, String usernameRealPlayer) {
        //Add human player
        players.add(new RealPlayer(usernameRealPlayer));

        // Management of computer player,
        //they have a username an avatars, but they are choose randomly.
        TypePlayer[] types = new TypePlayer[]{TypePlayer.BOT1, TypePlayer.BOT2, TypePlayer.BOT3};
        List<ComputerPlayer> availablePlayers = new ArrayList<>(
                IntStream.range(0, numberOfPlayers - 1)
                        .mapToObj(i -> new ComputerPlayer(USERNAMES[i], AVATARS[i], types[i]))
                        .toList()
        );
        Collections.shuffle(availablePlayers);

        // Add computer players
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            if (i < availablePlayers.size()) {
                players.add(availablePlayers.get(i));
            }
        }

        // Notify the observers
        List<String> usernames = players.stream().map(Player::getUsername).toList();
        List<String> avatar = players.stream().map(Player::getAvatar).toList();

        deck.shuffleDeck();

        setChanged();
        notifyObservers(new SetupPackage(PackageType.SETUP, usernames, numberOfPlayers, avatar));
        clearChanged();
    }

    /**
     * This method draws the initial cards for all players and the dealer.
     * Each player receive two cards.
     * The update is sent to the observers.
     */
    public void drawInitialCards() {
        List<Integer> scores = new ArrayList<>(players.stream().map(Player::getScore).toList());
        scores.add(dealer.getScore());

        // Draw two cards for each player
        for (Player player : players) {
            for (int i = 0; i < 2; i++) {
                GameCard card = deck.drawCard();
                player.hit(card);
                scores.set(players.indexOf(player), player.getScore());
                System.out.println(player.getType() + ": " + card.getSuit() + " " + card.getValue());
                setChanged();
                notifyObservers(new DrawPackage(PackageType.DRAW, card.getValue(), card.getSuit(), player.getType(), player.getScore()));
                notifyObservers(new ScorePackage(PackageType.SCORE, scores, player.getType()));
                clearChanged();
            }
        }

        // Draw two cards for the dealer
        GameCard firstCard = deck.drawCard();
        firstCard.setVisible(true);
        giveCard(scores, firstCard);


        GameCard secondCard = deck.drawCard();
        secondCard.setVisible(false);
        giveCard(scores, secondCard);
    }


    private void giveCard(List<Integer> scores, GameCard card) {
        dealer.hit(card);
        scores.set(players.size(), dealer.getScore());
        //System.out.println("Dealer riceve carta");
        setChanged();
        notifyObservers(new DrawDealerCardPackage(PackageType.DRAW_DEALER, card.getValue(), card.getSuit(), TypePlayer.DEALER, dealer.getScore(), card.getVisible()));
        // System.out.println("Notifica mandata");
        notifyObservers(new ScorePackage(PackageType.SCORE, scores, TypePlayer.DEALER));
        clearChanged();
    }

    /**
     * This method is called when a player decides to hit.
     *
     * @param player The player that decide to hit.
     */
    public void hit(Player player) {
        GameCard card = deck.drawCard();
        player.hit(card);
        setChanged();
        sleep(1000);
        notifyObservers(new HitPackage(PackageType.HIT, card.getValue(), card.getSuit(), player.getScore(), player.getType()));
        clearChanged();

    }

    /**
     * This method manage the dealer turn.
     * The dealer hit a card if his score is under 17.
     */
    public void dealerPlay() {

        dealer.setstanding(false);

        while (dealer.getScore() < 17) {
            GameCard card = deck.drawCard();
            dealer.hit(card);
            setChanged();
            //sleep to see the dealer's cards
            sleep(1000);
            notifyObservers(new HitPackage(PackageType.HIT, card.getValue(), card.getSuit(), dealer.getScore(), TypePlayer.DEALER));
            clearChanged();
        }
        dealer.stand();
    }

    /**
     * This method check the results between the players and the dealer.
     * It notifys the observer of win, loss, or tie
     */
    public void checkWin(List<Player> players, Player dealer) {

        //Check per i computer player
        for (int i = 1; i < players.size(); i++) {
            Player player = players.get(i);
            // se il player fa > 21 perde in automatico
            if (player.getScore() > 21) {
                sendLosePackage(player);
            } else if (dealer.getScore() > 21) {
                sendWinPackage(player);
            } else if (player.getScore() > dealer.getScore()) {
                sendWinPackage(player);
            } else if (player.getScore() < dealer.getScore()) {
                sendLosePackage(player);
            } else {
                sendTiePackage(player);
            }
        }

        RealPlayer realPlayer = (RealPlayer) players.get(0);
        if (realPlayer.getScore() > 21) {
            sendLosePackage(realPlayer);
            realPlayer.increaseLostGames();
        } else if (dealer.getScore() > 21) {
            sendWinPackage(realPlayer);
            realPlayer.increaseWonGames();
        } else if (realPlayer.getScore() > dealer.getScore()) {
            sendWinPackage(realPlayer);
            realPlayer.increaseWonGames();
        } else if (realPlayer.getScore() < dealer.getScore()) {
            sendLosePackage(realPlayer);
            realPlayer.increaseLostGames();
        } else {
            sendTiePackage(realPlayer);
            realPlayer.setTotalGames(realPlayer.getTotalGames() + 1);
        }

    }

    private void sendWinPackage(Player player) {
        setChanged();
        notifyObservers(new WinPackage(PackageType.WIN, true, player.getType()));
        clearChanged();
    }

    private void sendLosePackage(Player player) {
        setChanged();
        notifyObservers(new LosePackage(PackageType.LOSE, true, player.getType()));
        clearChanged();
    }

    private void sendTiePackage(Player player) {
        setChanged();
        notifyObservers(new TiePackage(PackageType.TIE, true, player.getType()));
        clearChanged();
    }

    /**
     * This method check if a player is bust.
     *
     * @param player
     * @return True if the player is bust, false otherwise.
     */
    public boolean checkBust(Player player) {
        boolean isBust = player.bust();
        if (isBust) {
            setChanged();
            notifyObservers(new BustPackage(PackageType.BUST, true, player.getType()));
            clearChanged();
        }
        return isBust;
    }

    /**
     * This method is called when a player decides to stand.
     *
     * @param player
     */
    public void stand(Player player) {
        player.setstanding(true);
    }

    /**
     * This method pause the game for an amount of time.
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
     * This method reset the game when the player decide to play again.
     */
    public void resetGame() {


        for (Player player : players) {
            //System.out.println(player.getType() + "tipo player");
            player.getHand().clearHand();
            player.setScore(0);
            player.setstanding(false);
            //System.out.println(" score player: " + player.getScore());
            setChanged();
            notifyObservers(new ResetPackage(PackageType.RESET, player.getType()));
            clearChanged();
        }

        dealer.getHand().clearHand();
        dealer.setScore(0);
        dealer.setstanding(false);
        //System.out.println(" score: " + dealer.getScore());
        setChanged();
        notifyObservers(new ResetPackage(PackageType.RESET, TypePlayer.DEALER));
        clearChanged();

        deck.initializeDeck();
        deck.shuffleDeck();
        System.out.println("Dim deck: " + deck.getDeckSize());
        // setChanged();
        // notifyObservers(new DeckResetPackage(PackageType.RESET_DECK, deck));
        // clearChanged();
    }

    /**
     * This method quit the game.
     */
    public void quitGame() {
        players.clear();
        deck = null;
        dealer = null;

        setChanged();
        notifyObservers();
    }

    /**
     * This method check if a player has a BlackJack.
     *
     * @param player
     * @return
     */
    public boolean checkBlackJack(Player player) {
        if (player.getScore() == 21) {
            setChanged();
            notifyObservers(new BlackJackPackage(PackageType.BLACKJACK, true, player.getType()));
            clearChanged();
            return true;
        }

        return false;
    }


}
