package org.blackjack.model;

import org.blackjack.api.*;
import org.blackjack.view.TypePlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.stream.IntStream;

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


    public BlackJackGame() {
        players = new ArrayList<>();
        deck = Deck.getInstance();
        dealer = new Dealer();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setUpGame(int numberOfPlayers, String usernameRealPlayer) {
        //Add human player
        players.add(new RealPlayer(usernameRealPlayer));

        // Management of computer player,
        //they have a username an avatars, but are choose randomly.
        TypePlayer[] types = new TypePlayer[]{TypePlayer.BOT1, TypePlayer.BOT2, TypePlayer.BOT3};
        List<ComputerPlayer> availablePlayers = new ArrayList<>(
                IntStream.range(0, numberOfPlayers - 1)
                        .mapToObj(i -> new ComputerPlayer(USERNAMES[i], AVATARS[i], types[i]))
                        .toList()
        );
        Collections.shuffle(availablePlayers);

        for (int i = 0; i < numberOfPlayers - 1; i++) {
            if (i < availablePlayers.size()) {
                players.add(availablePlayers.get(i));
            }
        }

        List<String> usernames = players.stream().map(Player::getUsername).toList();
        List<String> avatar = players.stream().map(Player::getAvatar).toList();

        deck.shuffleDeck();

        setChanged();
        notifyObservers(new SetupPackage(PackageType.SETUP, usernames, numberOfPlayers, avatar));

    }

    public void drawInitialCards() {
        List<Integer> scores = new ArrayList<>(players.stream().map(Player::getScore).toList());
        scores.add(dealer.getScore());
        for (Player player : players) {
            for (int i = 0; i < 2; i++) {
                GameCard card = deck.drawCard();
                player.hit(card);
                scores.set(players.indexOf(player), player.getScore());
                setChanged();
                notifyObservers(new DrawPackage(PackageType.DRAW, card.getValue(), card.getSuit(), player.getType(), player.getScore()));
                notifyObservers(new ScorePackage(PackageType.SCORE, scores, player.getType()));
                clearChanged();
            }
        }

        for (int i = 0; i < 2; i++) {
            GameCard card = deck.drawCard();
            dealer.hit(card);
            scores.set(players.size(), dealer.getScore());
            setChanged();
            notifyObservers(new DrawPackage(PackageType.DRAW, card.getValue(), card.getSuit(), TypePlayer.DEALER, dealer.getScore()));
            notifyObservers(new ScorePackage(PackageType.SCORE, scores, TypePlayer.DEALER));
            clearChanged();
        }
    }

    public void hit(Player player) {
        GameCard card = deck.drawCard();
        player.hit(card);
        setChanged();
        sleep(1000);
        notifyObservers(new HitPackage(PackageType.HIT, card.getValue(), card.getSuit(), player.getScore(), player.getType()));
        clearChanged();

    }


    public void dealerPlay() {
        dealer.setstanding(false);
        while (dealer.getScore() < 17) {
            GameCard card = deck.drawCard();
            dealer.hit(card);
            setChanged();
            sleep(1000);
            notifyObservers(new HitPackage(PackageType.HIT, card.getValue(), card.getSuit(), dealer.getScore(), TypePlayer.DEALER));
            clearChanged();
        }
        dealer.stand();
    }


    public void checkWin(List<Player> players, Player dealer) {
        for (int i = 0; i < players.size() - 1; i++) {
            Player player = players.get(i);
            if (player.getScore() > dealer.getScore()) {
                //vince player
                setChanged();
                notifyObservers(new WinPackage(PackageType.WIN, true, player.getType()));
                clearChanged();
            } else if (dealer.getScore() > player.getScore()) {
                //vince dealer
                setChanged();
                notifyObservers(new LosePackage(PackageType.LOSE, true, player.getType()));
                clearChanged();
            } else {
                // pareggio
                setChanged();
                notifyObservers(new TiePackage(PackageType.TIE, true, player.getType()));
                clearChanged();
            }

        }
    }

    //Se è bust ritorna true e lo passa tramite notify
    public boolean checkBust(Player player) {
        boolean isBust = player.bust();
        if (isBust) {
            setChanged();
            notifyObservers(new BustPackage(PackageType.BUST, true, player.getType()));
            clearChanged();
        }
        return isBust;
    }

    public void stand(Player player) {
        player.setstanding(true);
    }

    private void sleep(int milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public void resetGame() {
        for (Player player : players) {
            player.hand.clearHand();
            if (player instanceof RealPlayer && ((RealPlayer) player).hasSecondHand()) {
                ((RealPlayer) player).clearSecondHand();
            }
        }
        dealer.hand.clearHand();
        deck.refillDeck();
        deck.shuffleDeck();

        setChanged();
        notifyObservers();
    }

    public void quitGame() {
        players.clear();
        deck = null;
        dealer = null;

        setChanged();
        notifyObservers();
    }

    public boolean checkBlackJack(Player player) {
        if (player.getScore() == 21) {
            setChanged();
            notifyObservers(new BlackJackPackage(PackageType.BLACKJACK, true, player.getType()));
            clearChanged();
            return true;
        }

        return false;
    }


    public void playerBet(RealPlayer player) {
        player.setBet(player.getBet());
        player.setTotalFiches(player.getTotalFiches() - player.getBet());
    }


    //TODO: metodo notify ecc
    //public void
}
