package org.blackjack.api;

import org.blackjack.view.TypePlayer;

public record BlackJackPackage(PackageType packageType, boolean isBlackJack,
                               TypePlayer typePlayer) implements DataPackage {
}
