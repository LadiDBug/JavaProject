package org.blackjack.api;

import org.blackjack.model.Suit;
import org.blackjack.model.Value;

public record HitPackage(PackageType packageType, Value value, Suit suit,
                         int score) implements DataPackage {
}
