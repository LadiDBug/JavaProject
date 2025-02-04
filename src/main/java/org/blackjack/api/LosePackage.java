package org.blackjack.api;

import org.blackjack.view.TypePlayer;

/**
 * LosePackage class
 *
 * @param packageType
 * @param lose
 * @param typePlayer
 */
public record LosePackage(PackageType packageType, boolean lose, TypePlayer typePlayer) implements DataPackage {
}
