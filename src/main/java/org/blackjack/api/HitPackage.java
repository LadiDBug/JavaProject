package org.blackjack.api;

import org.blackjack.model.Suit;
import org.blackjack.model.Value;
import org.blackjack.view.TypePlayer;

public record HitPackage(PackageType packageType, Value value, Suit suit,
                         int score, TypePlayer typePlayer) implements DataPackage {
}
