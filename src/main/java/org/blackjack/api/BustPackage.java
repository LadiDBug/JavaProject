package org.blackjack.api;

import org.blackjack.view.TypePlayer;

/**
 * BustPackage class
 *
 * @param packageType
 * @param isBust
 * @param typePlayer
 */
public record BustPackage(PackageType packageType, boolean isBust, TypePlayer typePlayer) implements DataPackage {
}
