package org.blackjack.model;

import org.blackjack.api.DrawPackage;
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
        //they have a username an avatar, but are choose randomly.
        List<ComputerPlayer> availablePlayers = new ArrayList<>(
                IntStream.range(0, USERNAMES.length)
                        .mapToObj(i -> new ComputerPlayer(USERNAMES[i], AVATARS[i]))
                        .toList()
        );
        Collections.shuffle(availablePlayers);

        for (int i = 0; i < numberOfPlayers - 1; i++) {
            if (i < availablePlayers.size()) {
                players.add(availablePlayers.get(i));
            }
        }

        deck.shuffleDeck();
        System.out.print("I giocatori sono: " + players);

        setChanged();
        notifyObservers(new Object[]{players, dealer});

    }

    public void drawInitialCards() {
        for (Player player : players) {
            GameCard card = deck.drawCard();
            player.hit(card);
            setChanged();
            notifyObservers(new DrawPackage(card.getValue(), card.getSuit(), TypePlayer.PLAYER));
            clearChanged();
            player.hit(deck.drawCard());
        }

        dealer.hit(deck.drawCard());
        dealer.hit(deck.drawCard());

        setChanged();
        notifyObservers();
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
        if (player.hand.getHand().size() == 2) {
            if (player.getScore() == 21) {
                setChanged();
                notifyObservers();
                return true;
            }
        }
        return false;
    }

    public String checkWin(Player player, Player dealer) {
        String result;
        if (player.getScore() > dealer.getScore()) {
            result = player.getUsername();
        } else if (dealer.getScore() > player.getScore()) {
            result = "Dealer";
        } else {
            result = "Tie";
        }

        setChanged();
        notifyObservers(result);
        return result;
    }

    public boolean checkSplit(Player player) {
        if (player.hand.getHand().size() == 2) {
            GameCard firstCard = player.hand.getHand().get(0);
            GameCard secondCard = player.hand.getHand().get(1);

            return firstCard.getValue() == secondCard.getValue();
        }

        return false;
    }

    public boolean checkDoubleDown(Player player) {
        if (player.hand.getHand().size() == 2) {
            return player.getScore() >= 9 && player.getScore() <= 11;
        }
        return false;
    }

    public boolean checkBust(Player player) {
        boolean isBust = player.bust();
        if (isBust) {
            setChanged();
            notifyObservers(player.getUsername() + " is bust!");
        }
        return isBust;
    }


    public void playerBet(RealPlayer player) {
        player.setBet(player.getBet());
        player.setTotalFiches(player.getTotalFiches() - player.getBet());
    }


    public void splitManagement(RealPlayer player) {
        if (!checkSplit(player)) {
            //Errore
            return;
        }

        //creazione della seconda mano
        Hand secondHand = new Hand();
        GameCard secondCard = player.getHand().getHand().remove(1);
        secondHand.addCard(secondCard);

        // aggiungo carta alle due mani
        player.hit(deck.drawCard());
        secondHand.addCard(deck.drawCard());

        player.addSecondHand(secondHand);

        setChanged();
        notifyObservers();
    }

    //TODO: metodo notify ecc
    //public void
}
