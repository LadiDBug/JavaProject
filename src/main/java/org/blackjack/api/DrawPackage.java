package org.blackjack.api;

import org.blackjack.model.Suit;
import org.blackjack.model.Value;
import org.blackjack.view.TypePlayer;

//classe solo getter
public record DrawPackage(PackageType packageType, Value value, Suit suit, TypePlayer player) implements DataPackage {
}
