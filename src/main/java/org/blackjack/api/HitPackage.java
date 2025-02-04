package org.blackjack.api;

import org.blackjack.model.Suit;
import org.blackjack.model.Value;
import org.blackjack.view.TypePlayer;

/**
 * HitPackage class
 *
 * @param packageType
 * @param value
 * @param suit
 * @param score
 * @param typePlayer
 */
public record HitPackage(PackageType packageType, Value value, Suit suit,
                         int score, TypePlayer typePlayer) implements DataPackage {
}
