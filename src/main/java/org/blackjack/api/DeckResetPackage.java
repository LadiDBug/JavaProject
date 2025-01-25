package org.blackjack.api;

import org.blackjack.model.Deck;

public record DeckResetPackage(PackageType packageType, Deck deck) implements DataPackage {
}
