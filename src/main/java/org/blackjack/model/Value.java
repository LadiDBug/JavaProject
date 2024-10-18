package org.blackjack.model;
/**
 * An enumeration representing the values of the cards in a deck.
 * Each card value is associated with the point value used in the game.
 * 
 */
public enum Value {
	ACE(11),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7),
	EIGHT(8),
	NINE(9),
	TEN(10),
	JACK(10),
	QUEEN(10),
	KING(10);
	
	private final int points;
	
/**
 * Constructs a card value with the associated points.
 * 
 * @param points
 */
	Value(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return points;
	}
}
