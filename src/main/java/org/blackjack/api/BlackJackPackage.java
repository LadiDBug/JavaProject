package org.blackjack.api;

import org.blackjack.view.TypePlayer;

/**
 * BlackJackPackage class
 *
 * @param packageType
 * @param isBlackJack
 * @param typePlayer
 */
public record BlackJackPackage(PackageType packageType, boolean isBlackJack,
                               TypePlayer typePlayer) implements DataPackage {
}
