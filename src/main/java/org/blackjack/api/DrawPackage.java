package org.blackjack.api;

import org.blackjack.model.Suit;
import org.blackjack.model.Value;
import org.blackjack.view.TypePlayer;

/**
 * DrawPackage class
 *
 * @param packageType
 * @param value
 * @param suit
 * @param player
 * @param score
 */
public record DrawPackage(PackageType packageType, Value value, Suit suit, TypePlayer player,
                          int score) implements DataPackage {
}
