package org.blackjack.api;

import org.blackjack.model.Suit;
import org.blackjack.model.Value;
import org.blackjack.view.TypePlayer;

/**
 * DrawDealerCardPackage class
 *
 * @param packageType
 * @param value
 * @param suit
 * @param player
 * @param score
 * @param visible
 */
public record DrawDealerCardPackage(PackageType packageType, Value value, Suit suit, TypePlayer player,
                                    int score, boolean visible) implements DataPackage {
}
