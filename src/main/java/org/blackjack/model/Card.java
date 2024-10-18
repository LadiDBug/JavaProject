package org.blackjack.model;
/**
 * This interface represents a playing card.
 * Each card has a suit and a value.
 * The suit and the value can be returned by using the appropriate getter methods. 
 * 
 * @author Diana Pamfile
 **/
public interface Card {
	/**
	 * 
	 * @return the {@link Suit} of this card
	 */
	Suit getSuit();
	
	
	/**
	 * 
	 * @return the {@link Value} of the card
	 */
	Value getValue();
}
